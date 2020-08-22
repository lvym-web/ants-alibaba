package com.lvym.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope   //实时刷新，拉取配置
public class NacosController {

    @Value("${ants.name}")
    private String antsName;

    @GetMapping("/getInfo")
    public String getInfo(){
        return antsName;
    }
}
