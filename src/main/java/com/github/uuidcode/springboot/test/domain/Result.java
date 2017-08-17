package com.github.uuidcode.springboot.test.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.Assertions;

import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.utils.CoreUtil;

public class Result implements Serializable {
    public static final String KEY = "result";
    public static final String LAYOUT = "layout";
    public static final String ERROR_NOT_FOUND_PAGE = "페이지가 존재하지 않습니다.";
    public static final String ERROR = "오류가 발생하였습니다.";
    public static final String OK = "OK";

    private String message = OK;
    private List<Project> projectList;
    private String timestamp;
    private String error;
    private String path;
    private Project project;
    private Integer status;
    private Layout.LayoutType layoutType;

    public Layout.LayoutType getLayoutType() {
        return this.layoutType;
    }

    public Result setLayoutType(Layout.LayoutType layoutType) {
        this.layoutType = layoutType;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public Optional<String> getMessageOptional() {
        return Optional.ofNullable(this.message);
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public Result messageIsNull() {
        Assertions.assertThat(this.message).isNull();
        return this;
    }

    public Result messageIsNotNull() {
        Assertions.assertThat(this.message).isNotNull();
        return this;
    }

    public Result messageIsEqualTo(String message) {
        Assertions.assertThat(this.message).isEqualTo(message);
        return this;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Optional<Integer> getStatusOptional() {
        return Optional.ofNullable(this.status);
    }

    public Result setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Result statusIsNull() {
        Assertions.assertThat(this.status).isNull();
        return this;
    }

    public Result statusIsNotNull() {
        Assertions.assertThat(this.status).isNotNull();
        return this;
    }

    public Result statusIsEqualTo(Integer status) {
        Assertions.assertThat(this.status).isEqualTo(status);
        return this;
    }

    public Project getProject() {
        return this.project;
    }

    public Optional<Project> getProjectOptional() {
        return Optional.ofNullable(this.project);
    }

    public Result setProject(Project project) {
        this.project = project;
        return this;
    }

    public Result projectIsNull() {
        Assertions.assertThat(this.project).isNull();
        return this;
    }

    public Result projectIsNotNull() {
        Assertions.assertThat(this.project).isNotNull();
        return this;
    }

    public Result projectIsEqualTo(Project project) {
        Assertions.assertThat(this.project).isEqualTo(project);
        return this;
    }

    public String getPath() {
        return this.path;
    }

    public Result setPath(String path) {
        this.path = path;
        return this;
    }
    public String getError() {
        return this.error;
    }

    public Result setError(String error) {
        this.error = error;
        return this;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public Result setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public List<Project> getProjectList() {
        return this.projectList;
    }

    public Result setProjectList(List<Project> projectList) {
        this.projectList = projectList;
        return this;
    }

    public static Result of(Map map) {
        return CoreUtil.parseJson(CoreUtil.toJson(map), Result.class);
    }
}
