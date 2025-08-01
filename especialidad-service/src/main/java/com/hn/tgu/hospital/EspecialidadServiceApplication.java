package com.hn.tgu.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = "com.hn.tgu.hospital")
public class EspecialidadServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EspecialidadServiceApplication.class, args);
    }
}
