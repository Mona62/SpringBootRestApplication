/**
 * Project : Spring-boot-MVC application 
 * Used : spring-boot-starter-thymeleaf and spring-boot-starter-web
 * Author : Mona 
 * 
 */

package com.finra.spring;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.finra.spring.service.StorageService;

@SpringBootApplication
public class SpringBootRestfulApplication implements CommandLineRunner{
 
    @Resource
    StorageService storageService;
     
    public static void main(String[] args) {
        SpringApplication.run(SpringBootRestfulApplication.class, args);
    }
 
    @Override
    public void run(String... args) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}