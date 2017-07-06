package com.franklions.ailife.webapp.service.impl;


import com.franklions.ailife.webapp.domain.ApplyData;
import com.franklions.ailife.webapp.mapper.ApplyDataMapper;
import com.franklions.ailife.webapp.service.IApplyDataService;
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

    @Override
    public List<ApplyData> searchApply(String keys) {
        return applyDataMapper.selectByKeys(keys);
    }

    @Override
    public List<ApplyData> searchApplyByCode(String applycode) {
        return applyDataMapper.selectByCode(applycode);
    }
}
