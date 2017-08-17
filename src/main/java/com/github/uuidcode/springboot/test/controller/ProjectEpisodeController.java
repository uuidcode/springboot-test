package com.github.uuidcode.springboot.test.controller;

import com.github.uuidcode.springboot.test.service.ProjectEpisodeService;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProjectEpisodeController {
    @Resource
    private ProjectEpisodeService projectEpisodeService;

//    @RequestMapping(value = "/episode", method = RequestMethod.GET)
//    public Optional<ProjectEpisode> list() {
//        return this.projectEpisodeService.find(1L);
//    }
}



