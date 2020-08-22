package com.lvym.gateway.controller;

import com.lvym.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    /**
     * 同步网关配置
     *
     * @return
     */
    @RequestMapping("/synGatewayConfig")
    public String synGatewayConfig() {
        return gatewayService.initAllRoute();
    }
}
