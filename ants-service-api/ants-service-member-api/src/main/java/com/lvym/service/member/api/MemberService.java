package com.lvym.service.member.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    @GetMapping("/getMemberInfo")
    String getMemberInfo(@RequestParam("userId") Long userId);

    @RequestMapping("/gateWayCluster")
    String gateWayCluster(HttpServletRequest request);
}
