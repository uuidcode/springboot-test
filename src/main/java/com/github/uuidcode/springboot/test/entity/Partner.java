package com.github.uuidcode.springboot.test.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partner extends CoreEntity<Partner> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long partnerId;
    private String name;
    private Long projectId;

    public Long getProjectId() {
        return this.projectId;
    }

    public Partner setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }
    public String getName() {
        return this.name;
    }

    public Partner setName(String name) {
        this.name = name;
        return this;
    }
    public Long getPartnerId() {
        return this.partnerId;
    }

    public Partner setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
        return this;
    }
}
