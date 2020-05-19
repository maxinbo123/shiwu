package com.example.zkdata.config;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * Created by maxb on 2019/11/17.
 */
public class ZkDataInit {
    private static volatile ZkClient zkClient;


    /*public static void main(String[] args) {
        if (zkClient == null) {
            zkClient = new ZkClient("172.30.66.155:2181,172.30.66.155:2182,172.30.66.155:2183", 6000, 3000);
        }
        String val = zkClient.readData("/seata/store.db.dbType");
        System.out.println("jieguo: "+val);
    }*/
    public static void main(String[] args) {
        if (zkClient == null) {
            //zkClient = new ZkClient("172.30.66.155:2181,172.30.66.155:2182,172.30.66.155:2183", 6000, 3000);
            zkClient = new ZkClient("172.30.65.13:2181", 6000, 3000);
        }
        if (!zkClient.exists("/seata")) {
            zkClient.createPersistent("/seata", true);
        }
        //获取key对应的value值
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        // 使用properties对象加载输入流
        try {
            File file = ResourceUtils.getFile("classpath:zk-config.properties");
            InputStream in = new FileInputStream(file);
            properties.load(in);
            Set<Object> keys = properties.keySet();//返回属性key的集合
            for (Object key : keys) {
                boolean b = putConfig(key.toString(), properties.get(key).toString());
                System.out.print("结果："+b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param dataId
     * @param content
     * @return
     */
    public static boolean putConfig(final String dataId, final String content) {
        Boolean flag = false;
        String path = "/seata/" + dataId;
        if (!zkClient.exists(path)) {
            zkClient.create(path, content, CreateMode.PERSISTENT);
            flag = true;
        } else {
            zkClient.writeData(path, content);
            flag = true;
        }
        return flag;
    }
}
