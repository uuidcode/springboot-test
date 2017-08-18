package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public class CoreEntity<T> implements Serializable {
    @Transient
    private Long page;
    @Transient
    private Long size;

    public Long getSize() {
        return this.size;
    }

    public T setSize(Long size) {
        this.size = size;
        return (T) this;
    }

    public Long getPage() {
        return this.page;
    }
    
    public T setPage(Long page) {
        this.page = page;
        return (T) this;
    }

    public Long getOffset() {
        if (this.page == null) {
            return null;
        }

        if (this.size == null) {
            return null;
        }

        return (this.page - 1L) * this.size;
    }
}
