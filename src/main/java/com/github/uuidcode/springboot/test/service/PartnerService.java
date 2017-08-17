package com.github.uuidcode.springboot.test.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.uuidcode.springboot.test.entity.Partner;

@Service
@Transactional
public class PartnerService extends CoreService<Partner> {
}
