package com.github.uuidcode.springboot.test.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.uuidcode.springboot.test.domain.Result;
import com.github.uuidcode.springboot.test.utils.CoreUtil;

@Controller
public class ErrorController extends AbstractErrorController {
    public static final String ERROR_URI = "/error";
    public static final String API_ERROR_URL = "/api/error";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String getErrorPath() {
        return ERROR_URI;
    }

    @Autowired
    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(value = ERROR_URI)
    public String error(HttpServletRequest request, Model model) {
        Map<String, Object> errorMap = getErrorAttributes(request, false);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(">>> errorMap {} ", CoreUtil.toJson(errorMap));
        }

        Result result = Result.of(errorMap);

        if (result.getPath().startsWith("/api/")) {
            String message = result.getMessage();

            if (result.getStatus().equals(404)) {
                message = Result.ERROR_NOT_FOUND_PAGE;
            }

            return "redirect:" + API_ERROR_URL + "?message=" + CoreUtil.urlEncode(message);
        }

        model.addAllAttributes(errorMap);
        return "/error";
    }

    @RequestMapping(value = API_ERROR_URL)
    @ResponseBody
    public Result error(Result result) {
        return result;
    }
}
