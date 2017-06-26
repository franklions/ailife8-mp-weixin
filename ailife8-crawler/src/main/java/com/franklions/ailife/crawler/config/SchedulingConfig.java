package com.franklions.ailife.crawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import us.codecraft.webmagic.Spider;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/26.
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    Spider spider ;

    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        logger.info(">>>>>>>>>>>>> scheduled ... " + new Date());
        spider.run();
    }
}
