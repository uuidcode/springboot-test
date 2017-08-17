package com.github.uuidcode.springboot.test.controller;

import java.util.Optional;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;

import com.github.uuidcode.springboot.test.domain.Layout;
import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.utils.CoreUtil;
import com.samskivert.mustache.Mustache;

public class CoreController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private Mustache.Compiler compiler;

    public void set(Model model, Result result) {
       this.set(model, result, null);
    }

    public void set(Model model, Result result, String layoutPrefix) {
        Layout.LayoutType layoutType = Optional.ofNullable(result.getLayoutType())
            .orElse(Layout.LayoutType.DEFAULT);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(">>> layoutType: {}", CoreUtil.toJson(layoutType));
            this.logger.debug(">>> layoutPrefix: {}", CoreUtil.toJson(layoutPrefix));
        }

        model.addAttribute(Result.KEY, result);
        model.addAttribute(Result.LAYOUT,
            new Layout()
                .setCompiler(this.compiler)
                .setLayoutType(layoutType)
                .setLayoutPrefix(layoutPrefix));
    }
}
