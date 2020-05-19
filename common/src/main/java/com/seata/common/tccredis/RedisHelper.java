package com.seata.common.tccredis;

import redis.clients.jedis.JedisCluster;

/**
 * Created by maxb on 2020/5/12.
 */
public class RedisHelper extends  org.mengyun.tcctransaction.repository.helper.RedisHelper{

    public static <T> T execute(JedisCluster jedisCluster,JedisClusterCallback<T> callback){
        try{
            return callback.doInJedisCluster(jedisCluster);
        }finally {
            if(jedisCluster != null){

            }
        }
    }
}
