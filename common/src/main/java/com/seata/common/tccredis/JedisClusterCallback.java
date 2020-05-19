package com.seata.common.tccredis;

import redis.clients.jedis.JedisCluster;

/**
 * Created by maxb on 2020/5/12.
 */
public interface JedisClusterCallback<T> {

    public T doInJedisCluster(JedisCluster jedisCluster);
}
