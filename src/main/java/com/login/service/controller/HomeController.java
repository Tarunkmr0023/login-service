package com.login.service.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger LOG = LogManager.getLogger(HomeController.class);

    @GetMapping("/test")
    public String test() {
        LOG.warn("This is working message");
        return "Testing message";
    }
}
