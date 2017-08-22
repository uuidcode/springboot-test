package com.github.uuidcode.springboot.test.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;

public class Pagination {
    private JPAQuery query;
    private Pageable pageable;

    public Pagination paging(Pageable pageable) {
        if (pageable == null) {
            return this;
        }

        this.pageable = pageable;

        this.query.offset(pageable.getOffset())
            .limit(pageable.getPageSize());

        return this;
    }
    
    private Pagination setQuery(JPAQuery query) {
        this.query = query;
        return this;
    }
    
    public static Pagination of(JPAQuery query) {
        return new Pagination().setQuery(query);
    }

    public Pagination sorting(NumberPath path) {
        return this.sorting(path, path.getMetadata().getName());
    }

    public Pagination sorting(StringPath path) {
        return this.sorting(path, path.getMetadata().getName());
    }

    public Pagination sorting(EnumPath path) {
        return this.sorting(path, path.getMetadata().getName());
    }

    public Pagination sorting(DateTimePath path) {
        return this.sorting(path, path.getMetadata().getName());
    }

    public Pagination sorting(ComparableExpressionBase expression, String columnName) {
        if (this.pageable != null) {
            Sort sort = this.pageable.getSort();
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
