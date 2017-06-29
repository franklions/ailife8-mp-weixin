package com.franklions.ailife.crawler.service.impl;

import com.franklions.ailife.crawler.domain.ApplyData;
import com.franklions.ailife.crawler.mapper.ApplyDataMapper;
import com.franklions.ailife.crawler.service.IApplyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/27
 * @since Jdk 1.8
 */
@Service
public class ApplyDataServiceImpl implements IApplyDataService {

    @Autowired
    ApplyDataMapper applyDataMapper;

    @Override
    public boolean batchSave(List<ApplyData> dataMap) {
        return applyDataMapper.insertBatch(dataMap);
    }
}
