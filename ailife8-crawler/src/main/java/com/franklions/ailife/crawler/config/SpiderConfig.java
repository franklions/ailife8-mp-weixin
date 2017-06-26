package com.franklions.ailife.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by Administrator on 2017/6/26.
 */
@Configuration
public class SpiderConfig {

    @Bean
    Scheduler fileScheduler(){
        return Scheduler();
    }

    @Bean
    PageProcessor pageProcessor(){
        return new PageProcessor();
    }
    @Bean
    Pipeline mysqlPipline(){
        return new Pipeline();
    }

    @Bean
    Spider spider (){
        Spider spider = Spider.create(pageProcessor());
            spider.setScheduler(fileScheduler());
            spider.addUrl("http://apply.tjjttk.gov.cn/apply/norm/personQuery.html?201610");
            spider.addPipeline( mysqlPipline());
    }
}
