package com.franklions.ailife.webapp.service.impl;

import com.franklions.ailife.webapp.domain.ApplyPerson;
import com.franklions.ailife.webapp.mapper.ApplyPersonMapper;
import com.franklions.ailife.webapp.service.IApplyPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/6.
 */
@Service
public class ApplyPersonServiceImpl implements IApplyPersonService {
    @Autowired
    ApplyPersonMapper applyPersonMapper;

    @Override
    public boolean savePerson(ApplyPerson applyPerson) {

        return applyPersonMapper.insert(applyPerson) >0;
    }

    @Override
    public ApplyPerson getPersonBywxUser(String wxUser) {
        return applyPersonMapper.selectBywxUser(wxUser);
    }

    @Override
    public boolean updatePerson(ApplyPerson wxPerson) {
        return applyPersonMapper.updateByPrimaryKey(wxPerson) >0;
    }
}
