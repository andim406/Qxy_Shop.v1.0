package com.qxy.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;


@Configuration // 表示配置类
@PropertySource(value = "classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String nodes;
	/*
	 * 搭建redis集群
	 */
	@Bean
	public JedisCluster jedisCluster() {
			Set<HostAndPort> nodes = getNodes();
			return new JedisCluster(nodes);
	}
	
	//表示不要有重复数据
	private Set<HostAndPort> getNodes() {
		Set<HostAndPort> nodeSets = new HashSet<>();
		String[] steNode = nodes.split(",");
		for (String redisNode : steNode) {
			String host = redisNode.split(":")[0];
			int port = Integer.parseInt(redisNode.split(":")[1]);
			HostAndPort hostAndPort = new HostAndPort(host, port);
			nodeSets.add(hostAndPort);
		}
		return nodeSets;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	//mymaster:代表主机的变量名
	@Value("${redis.sentinel.masterName}")
	private String masterName;
	//IP和哨兵端口号
	@Value("${redis.sentinels}")
	private String nodes;
	@Bean(name="jedisSentinelPool")//表示是单例
	public JedisSentinelPool jedisSentinelPool() {
		Set<String> sentinels = new HashSet<>();
		sentinels.add(nodes);
		//1.获取连接池 两个对象	(1)mymaster (2)sentinels  是ip 和端口 
		JedisSentinelPool pool = new JedisSentinelPool(masterName, sentinels);
		return pool;
	}
	@Bean
	@Scope("prototype")//@Qualifier 表示指定Bean并赋值 用在方法中
	public Jedis jedis(@Qualifier("jedisSentinelPool")JedisSentinelPool pool) {
		//getResource获取主机
		 Jedis jedis = pool.getResource();
		return jedis;
	}
	
	
	
	//redis分片配置
	@Value("${redis.nodes}")
	private String nodes;
	@Bean
	public ShardedJedis shardedJedis() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		//1.用String[] 来接受配置的多个ID 取逗号之前的字符串
		String[] strNodes = nodes.split(",");
		for (String strNode: strNodes) {
			//遍历    IP : 端口
			String[] node = strNode.split(":");
			String host = node[0];//IP
			int port = Integer.parseInt(node[1]);//端口
			shards.add(new JedisShardInfo(host, port));
		}
		return new ShardedJedis(shards);
	}
			
	单个redis配置
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private Integer port;
	@Bean
	public Jedis jedis() {
		return new Jedis(host,port);
	}
	*/
	
}
