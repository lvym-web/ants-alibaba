package com.lvym.order.loadbalance;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalace {

     ServiceInstance getSingleAddres(List<ServiceInstance> serviceInstance);
}
