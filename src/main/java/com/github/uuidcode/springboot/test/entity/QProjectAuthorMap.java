package com.github.uuidcode.springboot.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectAuthorMap is a Querydsl query type for ProjectAuthorMap
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProjectAuthorMap extends EntityPathBase<ProjectAuthorMap> {

    private static final long serialVersionUID = -98771794L;

    public static final QProjectAuthorMap projectAuthorMap = new QProjectAuthorMap("projectAuthorMap");

    public final NumberPath<Long> authorId = createNumber("authorId", Long.class);

    public final NumberPath<Long> displayOrder = createNumber("displayOrder", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public QProjectAuthorMap(String variable) {
        super(ProjectAuthorMap.class, forVariable(variable));
    }

    public QProjectAuthorMap(Path<? extends ProjectAuthorMap> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectAuthorMap(PathMetadata metadata) {
        super(ProjectAuthorMap.class, metadata);
    }

}

