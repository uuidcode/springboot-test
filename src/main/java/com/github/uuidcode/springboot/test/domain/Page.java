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
    
    public List<T> getList() {
        return this.list;
    }
    
    public Page setList(List<T> list) {
        this.list = list;
        return this;
    }

    public Page() {}

    public Page(Long currentPage, Long pageSize, Long pageItemSize, Long totalCount) {
        this.init(currentPage, pageSize, pageItemSize, totalCount);
    }

    public Page(Long currentPage, Long pageSize, Long totalCount) {
        this.init(currentPage, pageSize, 10L, totalCount);
    }

    public static Page of(Long currentPage, Long pageSize, Long pageItemSize, Long totalCount) {
        return new Page(currentPage, pageSize, pageItemSize, totalCount);
    }

    public static Page of(Long currentPage, Long pageSize, Long totalCount) {
        return new Page(currentPage, pageSize, totalCount);
    }

    protected void init(Long currentPage, Long pageSize, Long pageItemSize, Long totalCount) {
        this.currentPage = currentPage;
        this.startPage = this.calculateStartPage(currentPage, pageSize);
        this.beforeStartPage = this.calculateBeforeStartPage(pageSize);
        this.beforeEndPage = this.calculateBeforeEndPage(pageSize);
        this.lastPage = this.calculateLastPage(pageItemSize, totalCount);
        this.endPage = this.calculateEndPage(pageSize);
        this.nextStartPage = this.calculateNextStartPage(pageSize);
        this.nextEndPage = this.calculateNextEndPage(pageSize);

    }

    protected Long calculateNextEndPage(Long pageSize) {
        if ((startPage + pageSize) <= lastPage) {
            if ((endPage + pageSize) <= lastPage) {
                return endPage + pageSize;
            }
            return lastPage;
        }
        return null;
    }

    protected Long calculateNextStartPage(Long pageSize) {
        if ((startPage + pageSize) <= lastPage) {
            return startPage + pageSize;
        }
        return null;
    }

    protected Long calculateBeforeEndPage(Long pageSize) {
        if (pageSize > startPage) {
            return null;
        }

        return beforeStartPage + pageSize - 1;

    }

    protected Long calculateBeforeStartPage(Long pageSize) {
        if (pageSize > startPage) {
            return null;
        }
        return startPage - pageSize;
    }

    protected Long calculateEndPage(Long pageSize) {
        if ((lastPage - startPage) >= pageSize) {
            return (startPage + pageSize - 1);
        }
        return lastPage;
    }

    protected Long calculateLastPage(Long pageItemSize, Long totalCount) {
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
        return currentPage;
    }

    public Long getLastPage() {
        return lastPage;
    }

    public Long getStartPage() {
        return startPage;
    }

    public Long getEndPage() {
        return endPage;
    }

    public Boolean hasBefore() {
        return beforeStartPage != null && beforeEndPage != null;
    }

    public Long getBeforeStartPage() {
        return beforeStartPage;
    }

    public Long getBeforeEndPage() {
        return beforeEndPage;
    }

    public Boolean hasNext() {
        return nextStartPage != null && nextEndPage != null;
    }

    public Long getNextStartPage() {
        return nextStartPage;
    }

    public Long getNextEndPage() {
        return nextEndPage;
    }
}
