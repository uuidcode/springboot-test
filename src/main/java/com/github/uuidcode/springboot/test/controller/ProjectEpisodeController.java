package com.github.uuidcode.springboot.test.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.github.uuidcode.springboot.test.service.EpisodeService;

@RestController
public class ProjectEpisodeController {
    @Resource
    private EpisodeService projectEpisodeService;

//    @RequestMapping(value = "/episode", method = RequestMethod.GET)
//    public Optional<ProjectEpisode> list() {
//        return this.projectEpisodeService.find(1L);
//    }
}



