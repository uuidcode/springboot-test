package com.github.uuidcode.springboot.test.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
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

    public Pagination sort(ComparableExpressionBase expression, String column) {
        if (pageable != null) {
            Sort sort = pageable.getSort();
            Sort.Order order = sort.getOrderFor(column);

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
