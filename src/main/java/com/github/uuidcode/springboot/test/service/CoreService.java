package com.github.uuidcode.springboot.test.service;

import static java.util.Optional.ofNullable;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPADeleteClause;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;

@Service
@Transactional
public class CoreService<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    public <T> T save(T t) {
        this.entityManager.persist(t);
        return t;
    }

    public <T> T findById(Long id) {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> tClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
        return this.entityManager.find(tClass, id);
    }

    public <T> List<T> findAll(EntityPath<T> entityPath, BooleanBuilder booleanBuilder) {
        if (booleanBuilder == null) {
            booleanBuilder = new BooleanBuilder();
        }

        return this.select(entityPath)
            .from(entityPath)
            .where(booleanBuilder)
            .fetch();
    }

    public <T> List<T> findAll(EntityPath<T> entityPath) {
        return this.findAll(entityPath, null);
    }

    public LocalDateTime now() {
        Query query = this.entityManager.createNativeQuery("select now()");
        Date date = (Date) query.getSingleResult();
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public void updateById(Supplier<Long> supplier, Consumer<T> consumer) {
        Optional<T> optional = ofNullable(this.findById(supplier.get()));
        optional.ifPresent(consumer::accept);
    }

    public void updateById(Long id, Consumer<T> consumer) {
        Optional<T> optional = ofNullable(this.findById(id));
        optional.ifPresent(consumer::accept);
    }

    public void deleteById(Long id) {
        Optional<T> projectOptional = ofNullable(this.findById(id));
        projectOptional.ifPresent(this.entityManager::remove);
    }

    public void deleteByIdList(List<Long> idList) {
        idList.forEach(this::deleteById);
    }

    private JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(this.entityManager);
    }

    public <T> JPAQuery<T> select(EntityPath<T> expression) {
        return this.queryFactory().query().select(expression);
    }

    public JPAQuery<Tuple> select(Expression<?>... expression) {
        return this.queryFactory().query().select(expression);
    }

    public JPAUpdateClause update(EntityPath<T> entityPath) {
        return this.queryFactory().update(entityPath);
    }

    public JPADeleteClause delete(EntityPath<T> entityPath) {
        return this.queryFactory().delete(entityPath);
    }
}