package com.seata.storage.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @Author: heshouyou
 * @Description  seata global configuration
 * @Date Created in 2019/1/24 10:28
 */
@Configuration
public class SeataAutoConfig {

    /**
     * autowired datasource config
     */
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @NacosValue(value = "${account.datasource.url}", autoRefreshed = true)
    private String dataUrl;

    @NacosValue(value = "${account.datasource.username}", autoRefreshed = true)
    private String username;

    @NacosValue(value = "${account.datasource.password}", autoRefreshed = true)
    private String password;

    @NacosValue(value = "${datasource.maxActive}", autoRefreshed = true)
    private String maxActive;

    @NacosValue(value = "${datasource.maxWait}", autoRefreshed = true)
    private String maxWait;
    /**
     * init durid datasource
     *
     * @Return: druidDataSource  datasource instance
     */
    @Bean
    public DruidDataSource druidDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataUrl);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
        druidDataSource.setInitialSize(0);
        druidDataSource.setMaxActive(Integer.valueOf(maxActive));
        druidDataSource.setMaxWait(Integer.valueOf(maxWait));
        druidDataSource.setMinIdle(0);
        druidDataSource.setValidationQuery("Select 1 from DUAL");
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(25200000);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(1800);
        druidDataSource.setLogAbandoned(true);
        return druidDataSource;
    }

    /**
     * init datasource proxy
     * @Param: druidDataSource  datasource bean instance
     * @Return: DataSourceProxy  datasource proxy
     */
    @Bean
    public DataSourceProxy dataSourceProxy(DruidDataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSourceProxy dataSourceProxy) {
        return new DataSourceTransactionManager(dataSourceProxy);
    }

    /**
     * init mybatis sqlSessionFactory
     * @Param: dataSourceProxy  datasource proxy
     * @Return: DataSourceProxy  datasource proxy
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSourceProxy);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));
        factoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return factoryBean.getObject();
    }

    /**
     * init global transaction scanner
     *
     * @Return: GlobalTransactionScanner
     */
   /* @Bean
    public GlobalTransactionScanner globalTransactionScanner(){
        return new GlobalTransactionScanner("storage-gts-seata-example", "my_test_tx_group");
    }*/
}
