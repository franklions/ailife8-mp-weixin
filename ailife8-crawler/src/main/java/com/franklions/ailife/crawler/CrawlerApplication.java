package com.franklions.ailife.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * Created by Administrator on 2017/6/20.
 */
@SpringBootApplication
public class CrawlerApplication implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(CrawlerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class,args);
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("server is running!");
    }
}
