package com.franklions.ailife.crawler.config;

import com.franklions.ailife.crawler.service.IApplyDataService;
import com.franklions.ailife.crawler.service.impl.ApplyDataServiceImpl;
import com.franklions.ailife.crawler.webmagic.ApplyDatabasePipeline;
import com.franklions.ailife.crawler.webmagic.ApplyPageProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.FileCacheQueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

/**
 * Created by Administrator on 2017/6/26.
 */
@Configuration
public class SpiderConfig {

    @Bean
    Scheduler applyFileScheduler(){
        Scheduler fileScheduler = new FileCacheQueueScheduler("F:\\log\\data");
        return fileScheduler;
    }

    @Bean
    PageProcessor pageProcessor(){
        return new ApplyPageProcessor();
    }
    @Bean
    Pipeline dbPipeline(IApplyDataService applyDataService){

        return new ApplyDatabasePipeline(applyDataService);
    }

    @Bean
    Spider applySpider (ApplicationContext applicationContext){

        IApplyDataService service = applicationContext.getBean(ApplyDataServiceImpl.class);

        Spider spider = Spider.create(pageProcessor());
        spider.setScheduler(applyFileScheduler());
        spider.addUrl("http://apply.tjjttk.gov.cn/apply/norm/personQuery.html?201706");

        spider.addPipeline( dbPipeline(service));
        return spider;
    }
}
