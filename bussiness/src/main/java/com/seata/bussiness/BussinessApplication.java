package com.seata.bussiness;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "com.seata.bussiness")
@EnableDiscoveryClient
@EnableDubbo(scanBasePackages = "com.seata.bussiness")
@ImportResource({ "classpath:applicationContext-traceId.xml" })
public class BussinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(BussinessApplication.class, args);
	}

}
