package com.example.demoswagger.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CeShiapi {

    Logger logger = LoggerFactory.getLogger(CeShiapi.class);

    @GetMapping("/abc")
    public String testSwagger() {
        logger.info("我是日志");
        System.out.println("aaaaaa");
        return "bbbbb";
    }
}
