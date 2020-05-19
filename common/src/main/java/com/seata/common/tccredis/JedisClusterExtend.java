package com.seata.common.tccredis;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maxb on 2020/5/12.
 */
public class JedisClusterExtend{


    private String redisClusterIp;
    private String pass;
    private GenericObjectPoolConfig jedisPoolConfig;


    public  JedisClusterExtend(String redisClusterIp,String pass){
        this(pass,redisClusterIp, new GenericObjectPoolConfig());
    }

    public  JedisClusterExtend(String redisClusterIp,String pass,GenericObjectPoolConfig genericObjectPoolConfig){
       this.pass = pass;
        this.redisClusterIp = redisClusterIp;
        this.jedisPoolConfig = genericObjectPoolConfig;
    }

    /**
     *  获取JedisCluster
     *  @Method_Name             ：getJedisCluster
     *
     *  @return redis.clients.jedis.JedisCluster
     *  @Creation Date           ：2018/6/13
     *  @Author                  ：zc.ding@foxmail.com
     */
    public JedisCluster getJedisCluster(){
        Set<HostAndPort> set = new HashSet<HostAndPort>();
        String[] arr = redisClusterIp.split(",");
        for(String host : arr){
            String[] ipPort = host.trim().split(":");
            if(ipPort.length < 2){
                throw new RuntimeException(ipPort + " is Invalid.");
            }
            set.add(new HostAndPort(ipPort[0], Integer.parseInt(ipPort[1])));
        }
        return new JedisCluster(set, 5000, 2000, 2,pass,jedisPoolConfig);
    }
}
