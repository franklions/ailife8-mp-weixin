package com.franklions.ailife.webapp.mapper;


import com.franklions.ailife.webapp.domain.ApplyPerson;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/6/27
 * @since Jdk 1.8
 */

@Mapper
public interface ApplyPersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ApplyPerson record);

    int insertSelective(ApplyPerson record);

    ApplyPerson selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ApplyPerson record);

    int updateByPrimaryKey(ApplyPerson record);

    ApplyPerson selectBywxUser(String wxUser);
}