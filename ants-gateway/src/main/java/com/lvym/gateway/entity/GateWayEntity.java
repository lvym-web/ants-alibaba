package com.lvym.gateway.entity;

import lombok.Data;

@Data
public class GateWayEntity {
    private Long id;
    private String routeId;
    private String routeName;
    private String routePattern;
    private String routeType;
    private String routeUrl;
}