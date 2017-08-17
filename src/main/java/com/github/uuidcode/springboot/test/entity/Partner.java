package com.github.uuidcode.springboot.test.entity;

import javax.persistence.*;

@Entity
@Table(indexes = {
    @Index(name = "partner_idx1", columnList = "projectId", unique = true)
})
public class Partner {
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
