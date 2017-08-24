package com.github.uuidcode.springboot.test.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QEpisode is a Querydsl query type for Episode
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QEpisode extends EntityPathBase<Episode> {

    private static final long serialVersionUID = 1874514245L;

    public static final QEpisode episode = new QEpisode("episode");

    public final QCoreEntity _super = new QCoreEntity(this);

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> episodeId = createNumber("episodeId", Long.class);

    public final EnumPath<Episode.EpisodeType> episodeType = createEnum("episodeType", Episode.EpisodeType.class);

    //inherited
    public final NumberPath<Long> modifiedBy = _super.modifiedBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final StringPath name = createString("name");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public QEpisode(String variable) {
        super(Episode.class, forVariable(variable));
    }

    public QEpisode(Path<? extends Episode> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEpisode(PathMetadata metadata) {
        super(Episode.class, metadata);
    }

}

