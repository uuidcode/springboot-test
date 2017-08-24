package com.github.uuidcode.springboot.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.uuidcode.springboot.test.CoreTest;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.utils.CoreUtil;

public class ProjectServiceTest extends CoreTest {
    protected static Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Resource
    private ProjectService projectService;

    @Resource
    private EpisodeService episodeService;

    @Test
    public void findById() {
        Project project = this.projectService.findById(1L);
        assertThat(project.getName()).isEqualTo("AAA");

        if (logger.isDebugEnabled()) {
            logger.debug(">>> findById project: {}", CoreUtil.toJson(project));
        }
    }

    @Test
    public void save() {
        this.projectService.save();
    }

    @Test
    public void update() {
        this.projectService.updateById(2L, project -> project.setName(CoreUtil.createUUID()));
    }

    @Test
    public void delete() {
        Project project = new Project().setName(CoreUtil.createUUID());
        this.projectService.save(project);
        this.projectService.deleteById(project.getProjectId());
    }

    @Test
    public void deleteByIdList() {
        List<Long> idList = IntStream.range(0, 10)
            .mapToObj(i -> new Project().setName(CoreUtil.createUUID()))
            .map(this.projectService::save)
            .map(Project::getProjectId)
            .collect(Collectors.toList());

        this.projectService.deleteByIdList(idList);
    }

    @Test
    public void deleteIdGreaterThan() {
        IntStream.range(0, 10)
            .mapToObj(i -> new Project().setName(CoreUtil.createUUID()))
            .map(this.projectService::save)
            .mapToLong(Project::getProjectId)
            .min()
            .ifPresent(this.projectService::deleteIdGreaterThan);
    }

    @Test
    public void insertAndSelect() {
        Project project = this.projectService.findById(3L);
        LocalDateTime now = LocalDateTime.now();
        this.projectService.updateById(project.getProjectId(), p -> p.setName(CoreUtil.formatDateTime(now)));

        List<Project> list = this.projectService.findAll(new Project()).getList();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> insertAndSelect list: {}", CoreUtil.toJson(list));
        }
    }

    @Test
    public void test() {
        this.projectService.test();
    }
}