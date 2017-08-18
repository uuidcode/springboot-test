package com.github.uuidcode.springboot.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.utils.CoreUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectServiceTest {
    protected static Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Resource
    private ProjectService projectService;

    @Test
    public void findById() {
        Project project = this.projectService.findById(1L);
        assertThat(project.getName()).isEqualTo("AAA");

        if (logger.isDebugEnabled()) {
            logger.debug(">>> findById project: {}", CoreUtil.toJson(project));
        }
    }

    @Test
    public void findAll() {
        List<Project> list = this.projectService.findAll();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> findAll list: {}", CoreUtil.toJson(list));
        }

        list = this.projectService.findAll(new Project().setProjectId(10L));

        if (logger.isDebugEnabled()) {
            logger.debug(">>> findAll list: {}", CoreUtil.toJson(list));
        }
    }

    @Test
    public void save() {
        Project project = new Project().setName(CoreUtil.createUUID());
        this.projectService.save(project);

        if (logger.isDebugEnabled()) {
            logger.debug(">>> save project: {}", CoreUtil.toJson(project));
        }
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
    public void insertAndSelect() {
        Project project = this.projectService.findById(3L);
        LocalDateTime now = LocalDateTime.now();
        this.projectService.updateById(project.getProjectId(), p -> p.setName(CoreUtil.formatDateTime(now)));

        List<Project> list = this.projectService.findAll();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> insertAndSelect list: {}", CoreUtil.toJson(list));
        }
    }

    @Test
    public void test() {
        this.projectService.test();
    }
}