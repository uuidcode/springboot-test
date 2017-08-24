package com.github.uuidcode.springboot.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;


/**
 * QCoreEntity is a Querydsl query type for CoreEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QCoreEntity extends EntityPathBase<CoreEntity<?>> {

    private static final long serialVersionUID = 462208216L;

    public static final QCoreEntity coreEntity = new QCoreEntity("coreEntity");

    public final NumberPath<Long> createdBy = createNumber("createdBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createdDate = createDateTime("createdDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> modifiedBy = createNumber("modifiedBy", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDate = createDateTime("modifiedDate", java.time.LocalDateTime.class);

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCoreEntity(String variable) {
        super((Class) CoreEntity.class, forVariable(variable));
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCoreEntity(Path<? extends CoreEntity> path) {
        super((Class) path.getType(), path.getMetadata());
    }

    @SuppressWarnings({"all", "rawtypes", "unchecked"})
    public QCoreEntity(PathMetadata metadata) {
        super((Class) CoreEntity.class, metadata);
    }

}

