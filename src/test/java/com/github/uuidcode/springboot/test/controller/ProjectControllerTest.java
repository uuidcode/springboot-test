package com.github.uuidcode.springboot.test.controller;

import org.junit.Test;

import com.github.uuidcode.springboot.test.CoreTest;
import com.github.uuidcode.springboot.test.utils.RequestBuilder;

public class ProjectControllerTest extends CoreTest {
    @Test
    public void api() {
        new RequestBuilder()
            .get(this.getHost() + "/api/project?page=2&size=10&sort=projectType,asc&sort=projectId,desc")
            .executeAndGetResult();
    }
}