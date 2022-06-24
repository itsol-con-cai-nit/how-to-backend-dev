package com.example.demooauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class DemoOauth2Application {

    public static void main(String[] args) {
        SpringApplication.run(DemoOauth2Application.class, args);
    }
}
