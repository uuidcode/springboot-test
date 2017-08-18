package com.github.uuidcode.springboot.test.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Author extends CoreEntity<Author> {
    public enum Status {
        SERVICE, WAITING
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private ProjectAuthorMap projectAuthorMap;

    public ProjectAuthorMap getProjectAuthorMap() {
        return this.projectAuthorMap;
    }

    public Author setProjectAuthorMap(ProjectAuthorMap projectAuthorMap) {
        this.projectAuthorMap = projectAuthorMap;
        return this;
    }

    public Status getStatus() {
        return this.status;
    }

    public Author setStatus(Status status) {
        this.status = status;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Author setName(String name) {
        this.name = name;
        return this;
    }
    public Long getAuthorId() {
        return this.authorId;
    }

    public Author setAuthorId(Long authorId) {
        this.authorId = authorId;
        return this;
    }
}
