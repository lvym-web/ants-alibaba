package com.lvym.service.member.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.lvym.service.member.api.MemberService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MemberServiceImpl implements MemberService {

    @Value("${server.port}")
    private String serverPort;


    //接口加了注解，这里可以通过实现继承过来
    @Override
    public String getMemberInfo(Long userId) {
        return "会员服务"+serverPort;
    }

    @Override
    public String gateWayCluster(HttpServletRequest request) {
        String serverPort = request.getHeader("serverPort");
        return "会员服务"+serverPort;
    }
}
