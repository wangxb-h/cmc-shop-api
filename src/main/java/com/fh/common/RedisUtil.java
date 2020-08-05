package com.fh.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {
    private static JedisPool jedisPool;
    private RedisUtil(){

    }
    static{
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        //最大连接数
        jedisPoolConfig.setMaxTotal(10);
        //设置最大空闲数
        jedisPoolConfig.setMaxIdle(10);
        //设置最小空闲数
        jedisPoolConfig.setMinIdle(5);
        //最大等待毫秒数
        jedisPoolConfig.setMaxWaitMillis(80000);
        //
        jedisPool=new JedisPool(jedisPoolConfig,"192.168.10.133",6379);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }
    public static void returnJedis(Jedis jedis){
        jedisPool.returnResource(jedis);
    }
}
