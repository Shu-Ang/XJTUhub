package com.xjtuhub.controller;

import com.xjtuhub.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private StringUtils stringUtils;
    @GetMapping("/hello")
    public String hello(){
        String uuid = stringUtils.simpleUUID_8();
        System.out.println(uuid);
        return uuid;
    }
}
