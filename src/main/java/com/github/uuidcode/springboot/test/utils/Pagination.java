package com.github.uuidcode.springboot.test.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;

public class Pagination {
    private JPAQuery query;
    private Pageable pageable;
    
    public Pageable getPageable() {
        return this.pageable;
    }
    
    public Pagination setPageable(Pageable pageable) {
        this.pageable = pageable;
        return this;
    }
    
    public JPAQuery getQuery() {
        return this.query;
    }
    
    public Pagination setQuery(JPAQuery query) {
        this.query = query;
        return this;
    }
    
    public static Pagination of(JPAQuery query) {
        return new Pagination().setQuery(query);
    }

    public Pagination orderBy(NumberPath path) {
        return this.orderBy(path, path.getMetadata().getName());
    }

    public Pagination orderBy(StringPath path) {
        return this.orderBy(path, path.getMetadata().getName());
    }

    public Pagination orderBy(EnumPath path) {
        return this.orderBy(path, path.getMetadata().getName());
    }

    public Pagination orderBy(DateTimePath path) {
        return this.orderBy(path, path.getMetadata().getName());
    }

    public Pagination orderBy(ComparableExpressionBase expression, String columnName) {
        if (pageable != null) {
            Sort sort = pageable.getSort();
            Sort.Order order = sort.getOrderFor(columnName);

            if (order != null) {
                this.query.orderBy(this.sort(expression, order));
            }
        }

        return this;
    }

    public OrderSpecifier<?> sort(ComparableExpressionBase expression, Sort.Order order) {
        if (order.isAscending()) {
            return expression.asc();
        }

        return expression.desc();

    }
}
