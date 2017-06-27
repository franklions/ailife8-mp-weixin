package com.franklions.ailife.crawler.mapper;

import com.franklions.ailife.crawler.domain.ApplyData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/27
 * @since Jdk 1.8
 */
@Mapper
public interface ApplyDataMapper {
    int deleteByPrimaryKey( @Param("id") Long id);

    int insert(@Param("record") ApplyData record);

    int insertSelective(@Param("record") ApplyData record);

    ApplyData selectByPrimaryKey(@Param("id") Long id);

    int updateByPrimaryKeySelective(@Param("record") ApplyData record);

    int updateByPrimaryKey(@Param("record") ApplyData record);
}
