package com.github.uuidcode.springboot.test.service;

import static java.util.Optional.ofNullable;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.querydsl.jpa.impl.JPAQuery;

@Service
@Transactional
public class CoreService<T> {
    @PersistenceContext
    protected EntityManager entityManager;

    public <T> T save(T t) {
        this.entityManager.persist(t);
        return t;
    }

    @SuppressWarnings("unchecked")
    public <T> T find(Long id) {
        ParameterizedType genericSuperClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> tClass = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
        return this.entityManager.find(tClass, id);
    }

    public LocalDateTime now() {
        Query query = this.entityManager.createNativeQuery("select now()");
        Date date = (Date) query.getSingleResult();
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public void update(Supplier<Long> supplier, Consumer<T> consumer) {
        Optional<T> optional = ofNullable(this.find(supplier.get()));
        optional.ifPresent(consumer::accept);
    }

    public void remove(Long id) {
        Optional<T> projectOptional = ofNullable(this.find(id));
        projectOptional.ifPresent(this.entityManager::remove);
    }

    public JPAQuery<T> query() {
        return new JPAQuery<>(this.entityManager);
    }
}