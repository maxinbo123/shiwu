package com.seata.order;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.seata.order")
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan({"com.seata.order.mapper"})
@EnableDubbo(scanBasePackages = "com.seata.order")
@NacosPropertySource(dataId = "com.gaoyang.kenan",autoRefreshed = true)
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}

}
