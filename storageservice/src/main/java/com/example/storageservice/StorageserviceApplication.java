package com.example.storageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({ "classpath*:applicationContext*.xml"})
@SpringBootApplication
public class StorageserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageserviceApplication.class, args);
	}

}
