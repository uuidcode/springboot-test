package com.github.uuidcode.springboot.test.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.uuidcode.springboot.test.domain.Layout;
import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.service.ProjectService;

@Controller
public class TestController extends CoreController {
    public static final String ERROR_MESSAGE = "Hello, World";

    @Resource
    private ProjectService projectService;

    @RequestMapping("/project1")
    public void testProject1(Model model) {
        Result result = new Result()
            .setProjectList(this.projectService.findAll())
            .setLayoutType(Layout.LayoutType.DEFAULT);
        this.set(model, result);
    }

    @RequestMapping("/project2")
    public void testProject2(Model model) {
        Result result =
            new Result()
                .setProjectList(this.projectService.findAll())
                .setLayoutType(Layout.LayoutType.TOP);
        this.set(model, result);
    }
}
