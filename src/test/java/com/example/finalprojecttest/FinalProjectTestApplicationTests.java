package com.example.finalprojecttest;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.finalprojecttest.controller.ReviewController;
import com.example.finalprojecttest.repository.ReviewRepository;
import com.example.finalprojecttest.service.ReviewService;
import com.example.finalprojecttest.service.UserService;

//@EnableJpaRepositories(basePackages="com.example.finalprojecttest.repository")
//@ComponentScan({ "com.example.finalprojecttest.*" })

@SpringBootTest(classes= ReviewService.class)
class FinalProjectTestApplicationTests {

    @Test
    void contextLoads() {
    }

}
