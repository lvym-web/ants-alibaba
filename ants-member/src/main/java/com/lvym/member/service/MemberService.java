package com.lvym.member.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberService {


    @Value("${server.port}")
    private String serverport;

    @GetMapping("/getMemberInfo")
    public String getMemberInfo(Long userId){
        return "hello-world:"+serverport;
    }

}
