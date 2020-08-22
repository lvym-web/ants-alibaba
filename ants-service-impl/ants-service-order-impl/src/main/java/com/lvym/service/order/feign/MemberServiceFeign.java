package com.lvym.service.order.feign;


import com.lvym.service.member.api.MemberService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("member-service")
public interface MemberServiceFeign extends MemberService {
}
