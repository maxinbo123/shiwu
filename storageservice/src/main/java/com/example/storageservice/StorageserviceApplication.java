package com.example.storageservice;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({ "classpath*:applicationContext*.xml"})
@SpringBootApplication
@NacosPropertySource(dataId = "com.gaoyang.kenan",autoRefreshed = true)
public class StorageserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageserviceApplication.class, args);
	}

}
