package com.github.uuidcode.springboot.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.uuidcode.springboot.test.domain.Layout;
import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.entity.Project;

@Controller
public class ControllerTest extends CoreController {
    public static final String ERROR_MESSAGE = "Hello, World";
    public static final String LAYOUT_PREFIX = "test/";

    @RequestMapping("/api/test")
    @ResponseBody
    public Result apiTest() {
        return new Result()
            .setProject(new Project().setProjectId(100L));
    }

    @RequestMapping("/api/errorTest")
    @ResponseBody
    public Result apiErrorTest() {
        if (true) {
            throw new RuntimeException(ERROR_MESSAGE);
        }

        return new Result();
    }

    @RequestMapping("/test/project/project1")
    public void testProject1(Model model) {
        Result result = new Result()
            .setProject(new Project().setProjectId(100L).setName("AAA"));
        this.set(model, result, LAYOUT_PREFIX);
    }

    @RequestMapping("/test/project/project2")
    public void testProject2(Model model) {
        Result result = new Result()
            .setProject(new Project().setProjectId(200L).setName("BBB"))
            .setLayoutType(Layout.LayoutType.TOP);
        this.set(model, result, LAYOUT_PREFIX);
    }
}
