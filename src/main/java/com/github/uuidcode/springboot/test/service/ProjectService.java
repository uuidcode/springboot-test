package com.github.uuidcode.springboot.test.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.entity.Author;
import com.github.uuidcode.springboot.test.entity.Partner;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.entity.QAuthor;
import com.github.uuidcode.springboot.test.entity.QEpisode;
import com.github.uuidcode.springboot.test.entity.QPartner;
import com.github.uuidcode.springboot.test.entity.QProject;
import com.github.uuidcode.springboot.test.entity.QProjectAuthorMap;
import com.github.uuidcode.springboot.test.utils.CoreUtil;
import com.github.uuidcode.springboot.test.utils.Pagination;
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

    @Override
    public Project findById(Long projectId) {
        Project project = super.findById(projectId);

        if (project != null) {
            project.setPartner(this.partnerService.findByProjectId(projectId));
        }

        return project;
    }

    public List<Project> findAll(Project project) {
        JPAQuery<Tuple> tupleJPAQuery = this.selectFromWhere();

        Pagination.of(tupleJPAQuery)
            .paging(project.getPageable())
            .sorting(qProject.projectId)
            .sorting(qProject.projectType);

        return tupleJPAQuery.fetch()
            .stream()
            .map(this::processProject)
            .collect(Collectors.toList());
    }

    public List<Project> findAll() {
        return this.findAll(null);
    }

    public Project processProject(Tuple tuple) {
        Project project = tuple.get(qProject);
        Partner partner = tuple.get(qPartner);
        Author author = tuple.get(qAuthor);

        return project.setPartner(partner)
            .setAuthor(author);
    }

    public Long findAllCount(Project project) {
        return this.selectFromWhere().fetchCount();
    }

    private JPAQuery<Tuple> selectFromWhere() {
        return this.select(qProject, qPartner, qAuthor,
                JPAExpressions.select(qEpisode.count())
                .from(qEpisode)
                .where(qEpisode.projectId.eq(qProject.projectId)))
            .from(qProject)
            .leftJoin(qPartner)
                .on(qPartner.projectId.eq(qProject.projectId))
            .leftJoin(qProjectAuthorMap)
                .on(qProjectAuthorMap.projectId.eq(qProject.projectId))
            .leftJoin(qAuthor)
                .on(qAuthor.authorId.eq(qProjectAuthorMap.authorId))
            .where(qProject.projectType.eq(Project.ProjectType.NORMAL));
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
}

