package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;

import javax.persistence.Transient;

public class CoreEntity<T> implements Serializable {
    @Transient
    private Integer page;
    @Transient
    private Integer size;

    public Integer getSize() {
        if (this.size == 0) {
            this.size = 10;
        }

        return this.size;
    }

    public T setSize(Integer size) {
        this.size = size;
        return (T) this;
    }

    public Integer getPage() {
        if (this.page == 0) {
            this.page = 1;
        }

        return this.page;
    }
    
    public T setPage(Integer page) {
        this.page = page;
        return (T) this;
    }

    public Integer getOffset() {
        return (this.getPage() - 1) * this.getPage();
    }
}
