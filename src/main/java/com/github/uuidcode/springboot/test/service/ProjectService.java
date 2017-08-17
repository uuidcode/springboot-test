package com.github.uuidcode.springboot.test.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

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
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;

@Transactional
@Service
public class ProjectService extends CoreService<Project> {
    private QProject qProject = QProject.project;
    private QAuthor qAuthor = QAuthor.author;
    private QProjectAuthorMap qProjectAuthorMap = QProjectAuthorMap.projectAuthorMap;
    private QPartner qPartner = QPartner.partner;
    @Resource
    private ProjectAuthorMapService projectAuthorMapService;

    @Resource
    private AuthorService authorService;

    @SuppressWarnings("all")
    public Project find(Long projectId) {
        Tuple tuple = this.query()
            .select(qProject, qPartner)
            .from(qProject)
            .leftJoin(qPartner).on(qPartner.projectId.eq(qProject.projectId))
            .where(qProject.projectId.eq(projectId))
            .fetchOne();

        if (tuple == null) {
            return null;
        }

        Project project = tuple.get(qProject);
        Partner partner = tuple.get(qPartner);

        if (project == null) {
            return null;
        }

        return project.setPartner(partner);
    }

    public List<Project> findAll(Project project) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        Long projectId = project.getProjectId();

        if (projectId != null) {
            booleanBuilder.and(qProject.projectId.gt(projectId));
        }

        return new JPAQuery<>(this.entityManager)
            .select(qProject)
            .from(qProject)
            .where(booleanBuilder)
            .fetch();
    }

    public List<Project> findAll() {
        return new JPAQuery<>(this.entityManager)
            .select(qProject)
            .from(this.qAuthor)
            .join(qProjectAuthorMap).on(qProjectAuthorMap.authorId.eq(this.qAuthor.authorId))
            .join(qProject).on(qProject.projectId.eq(this.qProjectAuthorMap.projectId))
            .where(this.qProject.projectType.eq(Project.ProjectType.SPECIAL))
            .fetch();
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
        this.update(project::getProjectId,
            p -> p.setName(CoreUtil.createUUID()));
    }
}

