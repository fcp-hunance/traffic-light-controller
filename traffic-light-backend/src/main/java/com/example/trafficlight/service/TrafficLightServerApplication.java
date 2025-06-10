package com.example.trafficlight.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.trafficlight")
public class TrafficLightServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrafficLightServerApplication.class, args);
    }
}