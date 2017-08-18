package com.github.uuidcode.springboot.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
}