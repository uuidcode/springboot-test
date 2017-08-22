package com.github.uuidcode.springboot.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.uuidcode.springboot.test.controller.ControllerTest;
import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.entity.Author;
import com.github.uuidcode.springboot.test.entity.Episode;
import com.github.uuidcode.springboot.test.entity.Partner;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.entity.ProjectAuthorMap;
import com.github.uuidcode.springboot.test.service.AuthorService;
import com.github.uuidcode.springboot.test.service.PartnerService;
import com.github.uuidcode.springboot.test.service.ProjectAuthorMapService;
import com.github.uuidcode.springboot.test.service.ProjectEpisodeService;
import com.github.uuidcode.springboot.test.service.ProjectService;
import com.github.uuidcode.springboot.test.utils.CoreUtil;
import com.github.uuidcode.springboot.test.utils.RequestBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringbootSampleApplicationTests {
    public final static String NEW_LINE = "\n";
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ProjectService projectService;

    @Resource
    private ProjectEpisodeService projectEpisodeService;

    @Resource
    private AuthorService authorService;

    @Resource
    private ProjectAuthorMapService projectAuthorMapService;

    @Resource
    private PartnerService partnerService;

    @Test
    public void project() {
        LocalDateTime now = this.projectService.now();

        String name = "한글";

        Project project =
            new Project()
                .setName(name)
                .setProjectType(Project.ProjectType.SPECIAL)
                .setAddedDateTime(now)
                .setUpdatedDateTime(now);

        this.projectService.save(project);

        this.projectService
            .findById(project.getProjectId())
            .nameIsEqualsTo(name);

        String newName = "테스트";
        Project.ProjectType projectType = Project.ProjectType.NORMAL;

        this.projectService.updateById(project::getProjectId,
            p -> p.setName(newName));

        this.projectService.updateById(project::getProjectId,
            p -> p.setProjectType(projectType));

        this.projectService.findById(project.getProjectId())
            .nameIsEqualsTo(newName)
            .projectTypeIsEqualsTo(projectType);

        CoreUtil.parseJson(CoreUtil.toJson(this.projectService.findById(project.getProjectId())), Project.class)
            .projectTypeIsEqualsTo(projectType);
    }

    @Test
    public void projectEpisode() {
        LocalDateTime now = this.projectService.now();

        Episode projectEpisode =
            new Episode()
                .setName(CoreUtil.createUUID())
                .setAddedDateTime(now)
                .setUpdatedDateTime(now)
                .setProjectId(1L);

        this.projectEpisodeService.save(projectEpisode);
    }

    @Test
    public void newAuthor() {
        final long[] index  = { 1L };

        Stream.<Author>builder()
            .add(new Author().setName("TED").setStatus(Author.Status.SERVICE))
            .add(new Author().setName("SALLY").setStatus(Author.Status.WAITING))
            .build()
            .forEach(a -> {
                this.authorService.save(a);
                this.projectAuthorMapService.save(
                    new ProjectAuthorMap()
                        .setAuthorId(a.getAuthorId())
                        .setProjectId(1L)
                        .setDisplayOrder(index[0]++));
            });
    }

    @Test
    public void authorServiceFindAll() {
        String json = CoreUtil.toJson(this.authorService.findAll(new Project().setProjectId(1L)));
        System.out.println(json);
    }

    @Test
    public void projectServiceFindAll() {
        this.logger.debug(">>> {}", CoreUtil.toJson(this.projectService.findAll()));
    }

    @Test
    public void partner() {
        Project project = this.projectService.findById(-1L);
        assertThat(project).isNull();

        if (this.partnerService.findById(1L) == null) {
            this.partnerService.save(new Partner().setName(CoreUtil.createUUID()).setProjectId(1L));
        }

        this.projectService.findById(1L).partnerIsNotNull();
        this.projectService.findById(2L).partnerIsNull();
    }

    @Test
    public void projectAudit() {
        Project project =
            new Project()
                .setProjectType(Project.ProjectType.SPECIAL)
                .setName(CoreUtil.createUUID());

        this.projectService.save(project);
        this.projectService.update(project.setName(CoreUtil.createUUID()));
        this.projectService.deleteById(project.getProjectId());
    }

    @Test
    public void episodeAudit() {
        Episode episode =
            new Episode()
                .setName(CoreUtil.createUUID())
                .setProjectId(1L);

        this.projectEpisodeService.save(episode);
        this.projectEpisodeService.update(episode.setName(CoreUtil.createUUID()));
        this.projectEpisodeService.deleteById(episode.getEpisodeId());
    }

    public static String getHost() {
        return "http://localhost:8080";
    }

    @Test
    public void api() {
        new RequestBuilder()
            .get(getHost() + "/api/test")
            .executeAndGetResult()
            .messageIsEqualTo(Result.OK)
            .getProject()
            .projectIdIsEqualTo(100L);

        new RequestBuilder()
            .get(getHost() +  "/api/testa")
            .executeAndGetResult()
            .messageIsEqualTo(Result.ERROR_NOT_FOUND_PAGE);

        new RequestBuilder()
            .get(getHost() +  "/api/errorTest")
            .executeAndGetResult()
            .messageIsEqualTo(ControllerTest.ERROR_MESSAGE);
    }

    @Test
    public void mustache() {
        String content =
            new RequestBuilder()
            .get(getHost() +  "/test/project/project1")
            .executeAndGetContent().trim();

        assertThat(content).isEqualTo(
            Stream.<String>builder()
                .add("default")
                .add("<ul>")
                .add("    <li>100:AAA</li>")
                .add("</ul>")
                .build()
                .collect(Collectors.joining(NEW_LINE)));

        content =
            new RequestBuilder()
                .get(getHost() +  "/test/project/project2")
                .executeAndGetContent().trim();

        assertThat(content).isEqualTo(
            Stream.<String>builder()
                .add("top")
                .add("<ul>")
                .add("    <li>200:BBB</li>")
                .add("</ul>")
                .build()
                .collect(Collectors.joining(NEW_LINE)));
    }

    @Test
    public void pageable() {
        String content = new RequestBuilder()
            .get(getHost() +  "/project3?page=2&size=10&sort=test,asc&sort=abc,desc&sort=tt,dd")
            .executeAndGetContent().trim();

        if (logger.isDebugEnabled()) {
            logger.debug(">>> pageable content: {}", content);
        }
    }
}
