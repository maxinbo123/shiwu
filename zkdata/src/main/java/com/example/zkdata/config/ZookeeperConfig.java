package com.example.zkdata.config;

import io.seata.common.thread.NamedThreadFactory;
import io.seata.config.AbstractConfiguration;
import io.seata.config.Configuration;
import io.seata.config.ConfigurationFactory;
import io.seata.config.zk.ZookeeperConfiguration;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * Created by maxb on 2019/11/17.
 */
public class ZookeeperConfig extends ZookeeperConfiguration {
    private final static Logger LOGGER = LoggerFactory.getLogger(io.seata.config.zk.ZookeeperConfiguration.class);



    private static final Configuration FILE_CONFIG = ConfigurationFactory.CURRENT_FILE_INSTANCE;
    private static volatile ZkClient zkClient;
    private static final int THREAD_POOL_NUM = 1;

    private static final ExecutorService CONFIG_EXECUTOR = new ThreadPoolExecutor(THREAD_POOL_NUM, THREAD_POOL_NUM,
            Integer.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new NamedThreadFactory("ZKConfigThread", THREAD_POOL_NUM));


    public ZookeeperConfig() {
        if (zkClient == null) {
            Class var1 = ZookeeperConfig.class;
            synchronized(ZookeeperConfig.class) {
                if (null == zkClient) {
                    zkClient = new ZkClient(FILE_CONFIG.getConfig("config.zk.serverAddr"), FILE_CONFIG.getInt("config.zk.session.timeout", 6000), FILE_CONFIG.getInt("config.zk.connect.timeout", 2000));
                }
            }

            if (!zkClient.exists("/config")) {
                zkClient.createPersistent("/config", true);
            }
        }

    }
    public boolean putConfig(final String dataId, final String content, long timeoutMills) {
        FutureTask<Boolean> future = new FutureTask(new Callable<Boolean>() {
            public Boolean call() throws Exception {
                String path = "/config/" + dataId;
                if (!ZookeeperConfig.zkClient.exists(path)) {
                    ZookeeperConfig.zkClient.create(path, content, CreateMode.PERSISTENT);
                } else {
                    ZookeeperConfig.zkClient.writeData(path, content);
                }

                return true;
            }
        });
        CONFIG_EXECUTOR.execute(future);

        try {
            return (Boolean)future.get(timeoutMills, TimeUnit.MILLISECONDS);
        } catch (Exception var7) {
            LOGGER.warn("putConfig {} : {} is error or timeout", dataId, content);
            return false;
        }
    }
}
