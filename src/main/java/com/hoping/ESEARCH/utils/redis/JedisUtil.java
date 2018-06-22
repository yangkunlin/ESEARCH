package com.hoping.ESEARCH.utils.redis;

import com.hoping.ESEARCH.common.RSParams;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;


/**
 * Author:  yangkunlin
 * Date:    2018/5/29
 * Domain:  pla.hc10
 */
public class JedisUtil {


  private static JedisCluster jedis;
  static {
    // 添加集群的服务节点Set集合
    Set<HostAndPort> hostAndPortsSet = new HashSet<>();
    // 添加节点
    hostAndPortsSet.add(new HostAndPort(RSParams.REDISCLUSTERHOST[0],RSParams.REDISCLUSTERPORT[0]));
    hostAndPortsSet.add(new HostAndPort(RSParams.REDISCLUSTERHOST[0],RSParams.REDISCLUSTERPORT[1]));
    hostAndPortsSet.add(new HostAndPort(RSParams.REDISCLUSTERHOST[1],RSParams.REDISCLUSTERPORT[0]));
    hostAndPortsSet.add(new HostAndPort(RSParams.REDISCLUSTERHOST[1],RSParams.REDISCLUSTERPORT[1]));
    hostAndPortsSet.add(new HostAndPort(RSParams.REDISCLUSTERHOST[2],RSParams.REDISCLUSTERPORT[0]));
    hostAndPortsSet.add(new HostAndPort(RSParams.REDISCLUSTERHOST[2],RSParams.REDISCLUSTERPORT[1]));

    // Jedis连接池配置
    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    // 最大空闲连接数, 默认8个
    jedisPoolConfig.setMaxIdle(100);
    // 最大连接数, 默认8个
    jedisPoolConfig.setMaxTotal(500);
    //最小空闲连接数, 默认0
    jedisPoolConfig.setMinIdle(0);
    // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
    jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
    //对拿到的connection进行validateObject校验
    jedisPoolConfig.setTestOnBorrow(true);
    jedis = new JedisCluster(hostAndPortsSet, jedisPoolConfig);
  }

  public static JedisCluster getJedis() {
    return jedis;
  }

}
