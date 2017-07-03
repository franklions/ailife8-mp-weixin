package com.franklions.ailife.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 * @description
 * @date 2017/7/1
 * @since Jdk 1.8
 */
@RestController
public class TestController {
    @GetMapping(value = "/test")
    public String test(){
        return " test value ";
    }
}
