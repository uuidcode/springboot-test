package com.github.uuidcode.springboot.test.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.github.uuidcode.springboot.test.CoreTest;
import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.utils.RequestBuilder;

public class ProjectControllerTest extends CoreTest {
    @Test
    public void api1() {
        Result result = new RequestBuilder()
            .get(this.getHost() + "/api/project?page=2&size=10&sort=projectType,asc&sort=projectId,desc&projectType=NORMAL")
            .executeAndGetResult();

        assertThat(result.getProjectPage().getList().size()).isGreaterThan(0);
    }

    @Test
    public void api2() {
        new RequestBuilder()
            .get(this.getHost() + "/api/project?page=1&size=5&sort=projectId,desc")
            .executeAndGetResult();
    }
}