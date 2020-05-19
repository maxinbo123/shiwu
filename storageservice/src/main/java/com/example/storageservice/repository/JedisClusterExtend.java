/*
package com.example.storageservice.repository;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

*/
/**
 * Created by maxb on 2020/3/26.
 *//*

public class JedisClusterExtend {

    private static ReentrantLock INSTANCE_INIT_LOCL = new ReentrantLock(false);

    private String address;
    private String password;
    private int connectTime;
    private int soTime;
    private int

    private static JedisCluster jedisCluster;;

    private static JedisCluster getJedisCluster(){
        if (jedisCluster == null) {
            try {
                if (INSTANCE_INIT_LOCL.tryLock(2, TimeUnit.SECONDS)) {
                    try {
                        if (jedisCluster == null) {

                            List<JedisShardInfo> jedisShardInfos = new LinkedList<JedisShardInfo>();
                            Set<HostAndPort> set =new HashSet<>();
                            String[] addressArr = address.split(",");
                            for (int i = 0; i < addressArr.length; i++) {
                                String[] hap = addressArr[i].split(":");
                                HostAndPort hostAndPort = new HostAndPort(hap[0],Integer.parseInt(hap[1]));
                                set.add(hostAndPort);
                            }

                            jedisCluster = new redis.clients.jedis.JedisCluster(set,5000,2000,2,password,new GenericObjectPoolConfig());
                        }

                    } finally {
                        INSTANCE_INIT_LOCL.unlock();
                    }
                }

            } catch (InterruptedException e) {
            }
        }

        if (jedisCluster == null) {
            throw new NullPointerException(">>>>>>>>>>> xxl-sso, JedisUtil.jedisCluster is null.");
        }
        return jedisCluster;
    }

}
*/
