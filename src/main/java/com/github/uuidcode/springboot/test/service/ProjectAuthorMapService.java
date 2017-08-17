package com.github.uuidcode.springboot.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.uuidcode.springboot.test.entity.ProjectAuthorMap;

@Service
@Transactional
public class ProjectAuthorMapService extends CoreService<ProjectAuthorMap> {

}
