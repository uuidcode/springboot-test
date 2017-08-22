package com.github.uuidcode.springboot.test.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.entity.Project;
import com.github.uuidcode.springboot.test.service.ProjectService;

@Controller
public class ProjectController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ProjectService projectService;

    @RequestMapping(value = "/api/project", method = RequestMethod.GET)
    @ResponseBody
    public Result apiList(Project project, Pageable pageable) {
        project.setPageable(pageable);
        return new Result().setProjectList(this.projectService.findAll(project));
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public void list(Model model, Project project) {
        Result result = new Result().setProjectList(this.projectService.findAll(project));
        model.addAttribute("result", result);
    }
}



