package com.franklions.ailife.webapp.service;

import com.franklions.ailife.webapp.domain.ApplyPerson;

/**
 * Created by Administrator on 2017/7/6.
 */
public interface IApplyPersonService {
    boolean savePerson(ApplyPerson applyPerson);

    ApplyPerson getPersonBywxUser(String wxUser);

    boolean updatePerson(ApplyPerson wxPerson);
}
