package com.lvym.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SentinelController {


    @SentinelResource(value = "baidu")
    @RequestMapping("/getSentinel")
    public String getSentinel(){
        return "hello";
    }

}
