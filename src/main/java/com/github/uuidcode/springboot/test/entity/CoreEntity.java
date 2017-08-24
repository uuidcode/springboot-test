package com.github.uuidcode.springboot.test.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.github.uuidcode.springboot.test.domain.Page;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CoreEntity<T> implements Serializable {
    @Transient
    private Long page;
    @Transient
    private Long size;
    @Transient
    private String orderBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime modifiedDate;
    @CreatedBy
    private Long createdBy;
    @LastModifiedBy
    private Long modifiedBy;
    
    public Long getModifiedBy() {
        return this.modifiedBy;
    }
    
    public CoreEntity setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }    
    public Long getCreatedBy() {
        return this.createdBy;
    }
    
    public CoreEntity setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }    
    public LocalDateTime getModifiedDate() {
        return this.modifiedDate;
    }
    
    public CoreEntity setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }    
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }
    
    public CoreEntity setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }
    
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
            this.page = 1L;
        }

        if (this.size == null) {
            this.size = 10L;
        }

        return (this.page - 1) * this.size;
    }

    public Page<T> toPage(List<T> list, Long totalCount) {
        return new Page<T>(this.page, this.size, totalCount, list);
    }
}
