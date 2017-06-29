package com.franklions.ailife.crawler.webmagic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franklions.ailife.crawler.domain.ApplyData;
import com.franklions.ailife.crawler.service.IApplyDataService;
import com.franklions.ailife.crawler.service.impl.ApplyDataServiceImpl;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/29
 * @since Jdk 1.8
 */
public class ApplyDatabasePipeline implements Pipeline {

    private static  IApplyDataService applyService;

    public ApplyDatabasePipeline( IApplyDataService applyDataService){
        applyService = applyDataService;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        System.out.println("get page: " + resultItems.getRequest().getUrl());
        //遍历所有结果，输出到控制台，上面例子中的"author"、"name"、"readme"都是一个key，其结果则是对应的value
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            System.out.println(entry.getKey() + ":\t" + entry.getValue());
        }


        String jsonStr = resultItems.get("applys");
        if(jsonStr != null && jsonStr != "")
        {
            ObjectMapper mapper = new ObjectMapper();
            try {
                List<ApplyData> dataList = mapper.readValue(jsonStr, new TypeReference<List<ApplyData>>() {});
               boolean flag = applyService.batchSave(dataList);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }
}
