package com.qxy.conf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

@PropertySource("classpath:/properties/redis.properties")
@Configuration//表示我是一个配置类
@Lazy//懒加载
public class RedisConfig {
	/**
	 * 搭建redis集群
	 * 
	 * @return
	 */
	@Value("${redis.nodes}")
	private String node;
	//配置集群的配置
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort>nodes=getNodes();
		return new JedisCluster(nodes);
	}
	//表示不要有重复数据
	private Set<HostAndPort> getNodes() {
		Set<HostAndPort>nodes=new HashSet<>();
		//把字符串 分成 ,号 0 ,1 ,2这样的
		String[] strNode=node.split(",");
		for(String redisNode : strNode) {
			//取:号前面的
			String host=redisNode.split(":")[0];
			//取:号后面的
			int port=Integer.parseInt(redisNode.split(":")[1]);
			HostAndPort hostAndPort = new HostAndPort(host,port);
			nodes.add(hostAndPort);
		}
		return nodes;
	}
	
}
