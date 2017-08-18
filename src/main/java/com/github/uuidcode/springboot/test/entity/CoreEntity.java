package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public class CoreEntity<T> implements Serializable {
    @Transient
    private Long page;
    @Transient
    private Long size;

    public Long getSize() {
        if (this.size == null) {
            this.size = 10L;
        }

        return this.size;
    }

    public T setSize(Long size) {
        this.size = size;
        return (T) this;
    }

    public Long getPage() {
        if (this.page == null) {
            this.page = 1L;
        }

        return this.page;
    }

    public T setPage(Long page) {
        this.page = page;
        return (T) this;
    }

    public Long getOffset() {
        return (this.page - 1L) * this.size;
    }
}
