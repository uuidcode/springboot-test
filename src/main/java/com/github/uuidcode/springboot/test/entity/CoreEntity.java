package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;

import javax.persistence.Transient;

import org.springframework.data.domain.Pageable;

public class CoreEntity<T> implements Serializable {
    @Transient
    private Pageable pageable;

    public Pageable getPageable() {
        return this.pageable;
    }

    public T setPageable(Pageable pageable) {
        this.pageable = pageable;
        return (T) this;
    }
}
