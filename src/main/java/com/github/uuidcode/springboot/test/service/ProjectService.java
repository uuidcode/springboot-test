package com.github.uuidcode.springboot.test.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.domain.Page;
import com.github.uuidcode.springboot.test.entity.Author;
import com.github.uuidcode.springboot.test.entity.Episode;
import com.github.uuidcode.springboot.test.entity.Partner;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.entity.QAuthor;
import com.github.uuidcode.springboot.test.entity.QEpisode;
import com.github.uuidcode.springboot.test.entity.QPartner;
import com.github.uuidcode.springboot.test.entity.QProject;
import com.github.uuidcode.springboot.test.entity.QProjectAuthorMap;
import com.github.uuidcode.springboot.test.utils.CoreUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

@Service
@Transactional
public class ProjectService extends CoreService<Project> {
    private QProject qProject = QProject.project;
    private QProjectAuthorMap qProjectAuthorMap = QProjectAuthorMap.projectAuthorMap;
    private QAuthor qAuthor = QAuthor.author;
    private QPartner qPartner = QPartner.partner;
    private QEpisode qEpisode = QEpisode.episode;

    @Resource
    private PartnerService partnerService;

    @Resource
    private EpisodeService episodeService;

    @Override
    public Project findById(Long projectId) {
        Project project = super.findById(projectId);

        if (project != null) {
            project.setPartner(this.partnerService.findByProjectId(projectId));
        }

        return project;
    }

    public Page<Project> findAll(Project project) {
        BooleanBuilder booleanBuilder = this.createBooleanBuilder(project);
        JPAQuery<Tuple> query = this.createQuery(booleanBuilder);
        this.sortingAndPaging(query, project);

        List<Project> projectList = this.map(query, this::mapping);
        Long totalCount = query.fetchCount();
        return project.toPage(projectList, totalCount);
    }

    public List<Project> findAllForSummary(Project project) {
        BooleanBuilder booleanBuilder = this.createBooleanBuilder(project);
        JPAQuery<Tuple> query = this.createQuery(booleanBuilder);
        return this.map(query, this::mapping);
    }

    private JPAQuery<Tuple> sortingAndPaging(JPAQuery<Tuple> query, Project project) {
        if ("projectTypeDesc".equals(project.getOrderBy())) {
            return query.orderBy(qProject.projectType.desc());
        }

        return query.orderBy(qProject.projectId.desc())
            .offset(project.getOffset())
            .limit(project.getSize());
    }

    public BooleanBuilder createBooleanBuilder(Project project) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Project.ProjectType projectType = project.getProjectType();

        if (projectType != null) {
            booleanBuilder.and(qProject.projectType.eq(projectType));
        }

        return booleanBuilder;
    }

    public Project mapping(Tuple tuple) {
        Project project = tuple.get(qProject);
        Partner partner = tuple.get(qPartner);
        Author author = tuple.get(qAuthor);

        return project.setPartner(partner)
            .setAuthor(author);
    }

    public Long findAllCount(Project project) {
        return this.createQuery().fetchCount();
    }

    private JPAQuery<Tuple> createQuery() {
        return this.createQuery(null);
    }

    private JPAQuery<Tuple> createQuery(BooleanBuilder booleanBuilder) {
        JPAQuery<Tuple> query = this.select(qProject, qPartner, qAuthor,
            JPAExpressions.select(qEpisode.count())
                .from(qEpisode)
                .where(qEpisode.projectId.eq(qProject.projectId)))
            .from(qProject)
            .leftJoin(qPartner)
            .on(qPartner.projectId.eq(qProject.projectId))
            .leftJoin(qProjectAuthorMap)
            .on(qProjectAuthorMap.projectId.eq(qProject.projectId))
            .leftJoin(qAuthor)
            .on(qAuthor.authorId.eq(qProjectAuthorMap.authorId));

        if (booleanBuilder == null) {
            return query;
        }

        return query.where(booleanBuilder);
    }

    public void update(Project project) {
        super.updateById(project::getProjectId,
            p -> p.setName(CoreUtil.createUUID()));
    }

    public void test() {
        Project project = this.findById(3L);
        LocalDateTime now = LocalDateTime.now();
        this.updateById(project.getProjectId(), p -> p.setName(CoreUtil.formatDateTime(now)));
    }

    public void deleteIdGreaterThan(Long id) {
        this.delete(qProject)
            .where(qProject.projectId.goe(id))
            .execute();
    }

    public Project save() {
        Project project = new Project().setName(CoreUtil.createUUID());
        this.save(project);

        this.episodeService.save(new Episode()
            .setProjectId(project.getProjectId())
            .setName(CoreUtil.createUUID()));

        this.episodeService.save(new Episode()
            .setProjectId(project.getProjectId())
            .setName(CoreUtil.createUUID()));

        return project;
    }
}

