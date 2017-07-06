package com.franklions.ailife.webapp.service;


import com.franklions.ailife.webapp.domain.ApplyData;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/27
 * @since Jdk 1.8
 */
public interface IApplyDataService {
    boolean batchSave(List<ApplyData> dataList);

    List<ApplyData> searchApply(String keys);

    List<ApplyData> searchApplyByCode(String applycode);
}
