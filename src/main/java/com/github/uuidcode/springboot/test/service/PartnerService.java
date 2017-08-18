package com.github.uuidcode.springboot.test.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.entity.Partner;
import com.github.uuidcode.springboot.test.entity.QPartner;

@Service
@Transactional
public class PartnerService extends CoreService<Partner> {
    private QPartner qPartner = QPartner.partner;

    public Partner findByProjectId(Long projectId) {
        return this.select(qPartner)
            .from(qPartner)
            .where(qPartner.projectId.eq(projectId))
            .fetchOne();
    }
}
