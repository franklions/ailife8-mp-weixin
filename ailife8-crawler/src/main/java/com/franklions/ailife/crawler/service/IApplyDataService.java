package com.franklions.ailife.crawler.service;

import com.franklions.ailife.crawler.domain.ApplyData;
import org.springframework.stereotype.Service;

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
}
