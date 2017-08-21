package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import org.springframework.data.domain.PageRequest;

public class CoreEntity<T> implements Serializable {
    @Transient
    private Integer page;
    @Transient
    private Integer size;
    @Transient
    private String orderBy;
    
    public String getOrderBy() {
        return this.orderBy;
    }
    
    public CoreEntity setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public Integer getSize() {
        return this.size;
    }

    public T setSize(Integer size) {
        this.size = size;
        return (T) this;
    }

    public Integer getPage() {
        return this.page;
    }

    public T setPage(Integer page) {
        this.page = page;
        return (T) this;
    }

    public PageRequest getPageRequest() {
        if (this.size == null) {
            this.size = 10;
        }

        if (this.page == null) {
            this.page = 1;
        }

        return new PageRequest(this.page - 1, this.size);
    }
}
