package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Transient;

import com.github.uuidcode.springboot.test.domain.Page;

public class CoreEntity<T> implements Serializable {
    @Transient
    private Long page;
    @Transient
    private Long size;
    @Transient
    private String orderBy;

    public String getOrderBy() {
        return this.orderBy;
    }

    public CoreEntity setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }
    public Long getSize() {
        return this.size;
    }

    public CoreEntity setSize(Long size) {
        this.size = size;
        return this;
    }

    public Long getPage() {
        return this.page;
    }

    public CoreEntity setPage(Long page) {
        this.page = page;
        return this;
    }

    public Long getOffset() {
        if (this.page == null) {
            return null;
        }

        if (this.size == null) {
            return null;
        }

        return (this.page - 1) * this.page;
    }

    public Page toPage(List<T> list, Long totalCount) {
        return Page.of(this.page, this.size, totalCount).setList(list);
    }
}
