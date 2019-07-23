package com.qxy.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.qxy.enu.KEY_ENUM;

//元注解,修饰注解的注解@Retention定义注解什么时候生效,RUNTIME:运行期间生效
//注解修饰的范围:ElementType.METHOD表示该注解修饰的方法
//@Target({ElementType.MeTHOD,ElementType.FIELD})//表示该注解修饰方法,和属性
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) //表示方法//注解的作用范围
public @interface Cache_Find {
	String key() default "";//表示接收用户key值 default:表示一个默认值
	KEY_ENUM keyType() default KEY_ENUM.AUTO;//keyType()表示类型,动态拼接id默认自动
	int secondes() default 0;//当前前值是否生效 默认永不失效
	
}
