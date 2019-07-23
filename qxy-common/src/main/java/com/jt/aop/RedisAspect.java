package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.qxy.anno.Cache_Find;
import com.qxy.enu.KEY_ENUM;
import com.qxy.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;



@Component //将对象交给spring容器管理
@Aspect	   //标识切面
public class RedisAspect {
	//表示当spring容器启动时不会立即注入对象
	@Autowired(required  =false)
	private JedisCluster jedisCluster;

	/**
	 * 1.环绕通知必须使用ProceedingJoinPoint
	 * 2. -joinPoint必须在第一位
	 */
	@SuppressWarnings("unchecked")
	//环绕通知的标准格式
	@Around(value = "@annotation(cache_Find)")//该切入点只能获取注解类型,名称必须匹配
	public Object around(ProceedingJoinPoint joinPoint,Cache_Find cache_Find) {
		//1.动态获取key
		String key = getKey(joinPoint,cache_Find);
		//2.从redis中获取数据
		String resultJSON = jedisCluster.get(key);
		Object resultData=null;
		try {
			//3.判断数据是否有值
			if(StringUtils.isEmpty(resultJSON)) {
				//3.1表示缓存中没有数据,则查询数据库(调用业务方法)
				resultData=joinPoint.proceed();//proceed()执行目标方法
				//3.2将数据保存到缓存中
				String json = ObjectMapperUtil.toJSON(resultData);
				//3.3判断当前的数据是否有失效时间

				if (cache_Find.secondes()==0) {
					jedisCluster.set(key, json);
				}else {
					jedisCluster.setex(key, cache_Find.secondes(), json);
				}
				System.out.println("AOP查询数据库");
			}else {
				//4.表示redis缓存中有数据
			Class returnType = getClass(joinPoint);
			resultData=ObjectMapperUtil.toObject(resultJSON,returnType);
			System.out.println("AOP查询缓存");
			}
			


		} catch (Throwable e) {
		}

		return resultData;
	}
	/**
	 * 表示获取方法对象的返回值类型
	 * @param joinPoint
	 * @return
	 */
	private Class getClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature=(MethodSignature) joinPoint.getSignature();

		return signature.getReturnType();
	}
	/**
	 *1.如果用户使用的AUTO.自动生成key 方法名_第一个参数
	 *2.如果用户 使用EMPTY,使用用户自己的key 
	 */
	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cache_Find) {
		//1.判断用户选择的类型
		if(KEY_ENUM.EMPTY.equals(cache_Find.keyType())) {
			return cache_Find.key();
		}
		//2.表示用户动态生成key
		// 	getSignature获得目标方法
		String methodName =joinPoint.getSignature().getName();
		//getArgs()获得参数
		String arg0 = String.valueOf(joinPoint.getArgs()[0]);


		return methodName+"::"+arg0; 
	}



	/*
	//当spring 容器启动时不会立即注入对象
	@Autowired(required = false)
	private JedisCluster jedisCluster;
	 * 1.环绕通知必须使用ProceedingJoinPoint
	 * 2.如果通知中有参数joinPoint,必须位于第一位


	//该切入点表达式 规定只能获取注解类型 用法名称必须匹配
	@Around(value="@annotation(cache_Find)")
	public Object around(ProceedingJoinPoint joinPoint,Cache_Find cache_Find) {
		//1.动态获取
	}
	 */



}
