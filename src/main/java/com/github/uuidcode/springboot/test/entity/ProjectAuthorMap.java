package com.github.uuidcode.springboot.test.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProjectAuthorMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long displayOrder;
    private Long projectId;
    private Long authorId;

    public Long getAuthorId() {
        return this.authorId;
    }

    public ProjectAuthorMap setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }
    public Long getProjectId() {
        return this.projectId;
    }

    public ProjectAuthorMap setProjectId(Long projectId) {
        this.projectId = projectId;
        return this;
    }

    public Long getDisplayOrder() {
        return this.displayOrder;
    }

    public ProjectAuthorMap setDisplayOrder(Long displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public ProjectAuthorMap setId(Long id) {
        this.id = id;
        return this;
    }
}
