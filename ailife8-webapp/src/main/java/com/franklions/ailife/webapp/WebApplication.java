package com.franklions.ailife.webapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/1
 * @since Jdk 1.8
 */
@SpringBootApplication
public class WebApplication implements CommandLineRunner{
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>>running>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
