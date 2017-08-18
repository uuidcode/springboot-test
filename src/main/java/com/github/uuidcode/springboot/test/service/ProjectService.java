package com.github.uuidcode.springboot.test.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.entity.Author;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.entity.ProjectAuthorMap;
import com.github.uuidcode.springboot.test.entity.QProject;
import com.github.uuidcode.springboot.test.utils.CoreUtil;
import com.querydsl.core.BooleanBuilder;

@Service
@Transactional
public class ProjectService extends CoreService<Project> {
    private QProject qProject = QProject.project;

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
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (project != null) {
            Long projectId = project.getProjectId();

            if (projectId != null) {
                booleanBuilder.and(qProject.projectId.gt(projectId));
            }
        }

        return this.findAll(qProject, booleanBuilder);
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
}

