package com.example.finalprojecttest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan({ "com.example.finalprojecttest" })
@SpringBootApplication
public class FinalProjectTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectTestApplication.class, args);
    }

}
