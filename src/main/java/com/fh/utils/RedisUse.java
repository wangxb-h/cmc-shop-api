package com.fh.utils;

import com.alibaba.fastjson.JSONObject;
import com.fh.common.RedisUtil;
import com.fh.entity.AreaInfo;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUse {

    public static void set(String key,String value){
        Jedis jedis = RedisUtil.getJedis();
        jedis.set(key,value);
        RedisUtil.returnJedis(jedis);
    }

    public static void set(String key,String value,int seconds){
        Jedis jedis = RedisUtil.getJedis();
        jedis.setex(key,seconds,value);
        RedisUtil.returnJedis(jedis);
    }

    public static String get(String key){
        Jedis jedis = RedisUtil.getJedis();
        String value=jedis.get(key);
        RedisUtil.returnJedis(jedis);
        return value;
    }

    public static void hset(String key,String filed,String value){
        Jedis jedis = RedisUtil.getJedis();
        Long hset = jedis.hset(key, filed, value);
        RedisUtil.returnJedis(jedis);
    }

    public static long hlen(String key){
        Jedis jedis = RedisUtil.getJedis();
        Long hlen = jedis.hlen(key);
        RedisUtil.returnJedis(jedis);
        return hlen;
    }

    public static String hget(String key,String filed){
        Jedis jedis = RedisUtil.getJedis();
        String hget = jedis.hget(key, filed);
        RedisUtil.returnJedis(jedis);
        return hget;
    }

    public static List<String> hvals(String key){
        Jedis jedis = RedisUtil.getJedis();
        List<String> hvals = jedis.hvals(key);
        RedisUtil.returnJedis(jedis);
        return hvals;
    }
    //ids  id，id，id
    public static String getAreaNames(String areaIds) {
        Jedis jedis = RedisUtil.getJedis();
        StringBuffer sb=new StringBuffer();//中文名
        String[] split = areaIds.split(",");
        for (int i = 0; i <split.length ; i++) {
            String areaId=split[i];
            String areaStr = jedis.hget("area_jgxi", areaId);
             //将json字符串转为对象
            AreaInfo area = JSONObject.parseObject(areaStr, AreaInfo.class);
            sb.append(area.getAreaName()).append(",");
        }
        RedisUtil.returnJedis(jedis);
        return sb.toString();
    }

    public static void hdel(String key,String filed){
        Jedis jedis = RedisUtil.getJedis();
        jedis.hdel(key,filed);
        RedisUtil.returnJedis(jedis);
    }

    public static boolean exists(String key){
        Jedis jedis = RedisUtil.getJedis();
        Boolean exists = jedis.exists(key);
        RedisUtil.returnJedis(jedis);
        return exists;
    }

}
