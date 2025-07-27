package com.hn.tgu.hospital.eureka_server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class eurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(eurekaServer.class, args);
    }
}