package com.github.uuidcode.springboot.test.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.entity.Author;
import com.github.uuidcode.springboot.test.entity.Partner;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.entity.ProjectAuthorMap;
import com.github.uuidcode.springboot.test.entity.QAuthor;
import com.github.uuidcode.springboot.test.entity.QPartner;
import com.github.uuidcode.springboot.test.entity.QProject;
import com.github.uuidcode.springboot.test.entity.QProjectAuthorMap;
import com.github.uuidcode.springboot.test.utils.CoreUtil;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

@Service
@Transactional
public class ProjectService extends CoreService<Project> {
    protected static Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Resource
    private ProjectAuthorMapService projectAuthorMapService;
    @Resource
    private PartnerService partnerService;
    @Resource
    private AuthorService authorService;

    public Project findById(Long projectId) {
        Project project = super.findById(projectId);

        if (project != null) {
            project.setPartner(this.partnerService.findByProjectId(projectId));
        }

        return project;
    }

    public List<Project> findAll(Project project) {
        return this.selectFromWhere()
            .offset(project.getOffset())
            .limit(project.getSize())
            .fetch()
            .stream()
            .map(tuple -> {
                Project currentProject = tuple.get(QProject.project);
                Partner currentPartner = tuple.get(QPartner.partner);
                Author currentAuthor = tuple.get(QAuthor.author);

                return currentProject.setPartner(currentPartner)
                    .setAuthor(currentAuthor);
            })
            .collect(Collectors.toList());
    }

    public Long count(Project project) {
        return this.selectFromWhere().fetchCount();
    }

    private JPAQuery<Tuple> selectFromWhere() {
        return this.select(QProject.project, QPartner.partner, QAuthor.author)
            .from(QProject.project)
            .leftJoin(QPartner.partner)
                .on(QPartner.partner.projectId.eq(QProject.project.projectId))
            .leftJoin(QProjectAuthorMap.projectAuthorMap)
                .on(QProjectAuthorMap.projectAuthorMap.projectId.eq(QProject.project.projectId))
            .leftJoin(QAuthor.author)
                .on(QAuthor.author.authorId.eq(QAuthor.author.authorId))
            .where(QProject.project.projectType.eq(Project.ProjectType.NORMAL));
    }

    public List<Project> findAll() {
        Project project = null;
        return this.findAll(project);
    }

    public void save(Author author) {
        Project project = new Project().setProjectType(Project.ProjectType.SPECIAL).setName("Hello");
        this.save(project);
        this.authorService.save(author);
        this.projectAuthorMapService.save(new ProjectAuthorMap()
            .setProjectId(project.getProjectId())
            .setAuthorId(author.getAuthorId())
            .setDisplayOrder(1L));
    }

    public void update(Project project) {
        super.updateById(project::getProjectId,
            p -> p.setName(CoreUtil.createUUID()));
    }

    public void test() {
        Project project = this.findById(3L);
        LocalDateTime now = LocalDateTime.now();
        this.updateById(project.getProjectId(), p -> p.setName(CoreUtil.formatDateTime(now)));

        List<Project> list = this.findAll();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> insertAndSelect list: {}", CoreUtil.toJson(list));
        }
    }

    public void deleteIdGreaterThan(Long id) {
        this.delete(QProject.project)
            .where(QProject.project.projectId.goe(id))
            .execute();
    }
}

