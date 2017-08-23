package com.github.uuidcode.springboot.test.domain;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private Long currentPage;
    private Long lastPage;
    private Long startPage;
    private Long endPage;
    private Long beforeStartPage;
    private Long beforeEndPage;
    private Long nextStartPage;
    private Long nextEndPage;
    private List<T> list;

    public Page() {}

    public Page(Long currentPage, Long pageSize, Long pageItemSize, Long totalCount, List<T> list) {
        this.init(currentPage, pageSize, pageItemSize, totalCount, list);
    }

    public Page(Long currentPage, Long pageSize, Long totalCount, List<T> list) {
        this.init(currentPage, pageSize, 10L, totalCount, list);
    }

    private void init(Long currentPage, Long pageSize, Long pageItemSize, Long totalCount, List<T> list) {
        this.currentPage = currentPage;
        this.startPage = this.calculateStartPage(currentPage, pageSize);
        this.beforeStartPage = this.calculateBeforeStartPage(pageSize);
        this.beforeEndPage = this.calculateBeforeEndPage(pageSize);
        this.lastPage = this.calculateLastPage(pageItemSize, totalCount);
        this.endPage = this.calculateEndPage(pageSize);
        this.nextStartPage = this.calculateNextStartPage(pageSize);
        this.nextEndPage = this.calculateNextEndPage(pageSize);
        this.list = list;
    }

    public List<T> getList() {
        return this.list;
    }

    public Page setList(List<T> list) {
        this.list = list;
        return this;
    }

    private Long calculateNextEndPage(Long pageSize) {
        if ((this.startPage + pageSize) <= this.lastPage) {
            if ((this.endPage + pageSize) <= this.lastPage) {
                return this.endPage + pageSize;
            }

            return this.lastPage;
        }

        return null;
    }

    private Long calculateNextStartPage(Long pageSize) {
        if ((this.startPage + pageSize) <= this.lastPage) {
            return this.startPage + pageSize;
        }

        return null;
    }

    private Long calculateBeforeEndPage(Long pageSize) {
        if (pageSize > this.startPage) {
            return null;
        }

        return this.beforeStartPage + pageSize - 1;

    }

    private Long calculateBeforeStartPage(Long pageSize) {
        if (pageSize > this.startPage) {
            return null;
        }

        return this.startPage - pageSize;
    }

    private Long calculateEndPage(Long pageSize) {
        if ((this.lastPage - this.startPage) >= pageSize) {
            return (this.startPage + pageSize - 1);
        }

        return this.lastPage;
    }

    private Long calculateLastPage(Long pageItemSize, Long totalCount) {
        if (totalCount <= pageItemSize) {
            return 1L;
        }

        if (this.isMultiple(pageItemSize, totalCount)) {
            return totalCount / pageItemSize;
        }

        return totalCount / pageItemSize + 1;

    }

    private Boolean isMultiple(Long lower, Long larger) {
        return (larger % lower) == 0;
    }

    private Long calculateStartPage(Long currentPage, Long pageSize) {
        if (currentPage <= pageSize) {
            return 1L;
        }

        Long mod = currentPage % pageSize;

        if (mod == 0L) {
            mod = pageSize;
        }

        return currentPage - (mod - 1);
    }

    public Long getCurrentPage() {
        return this.currentPage;
    }

    public Long getLastPage() {
        return this.lastPage;
    }

    public Long getStartPage() {
        return this.startPage;
    }

    public Long getEndPage() {
        return this.endPage;
    }

    public Boolean hasBefore() {
        return this.beforeStartPage != null && this.beforeEndPage != null;
    }

    public Long getBeforeStartPage() {
        return this.beforeStartPage;
    }

    public Long getBeforeEndPage() {
        return this.beforeEndPage;
    }

    public Boolean hasNext() {
        return this.nextStartPage != null && this.nextEndPage != null;
    }

    public Long getNextStartPage() {
        return this.nextStartPage;
    }

    public Long getNextEndPage() {
        return this.nextEndPage;
    }
}
