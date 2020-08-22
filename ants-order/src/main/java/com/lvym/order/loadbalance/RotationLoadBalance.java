package com.lvym.order.loadbalance;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Component
public class RotationLoadBalance implements LoadBalace {

    private AtomicInteger atomicInteger=new AtomicInteger(0);//记录访问次数

    @Override
    public ServiceInstance getSingleAddres(List<ServiceInstance> serviceInstance) {

        int index = atomicInteger.incrementAndGet() % serviceInstance.size();

        return serviceInstance.get(index);
    }
}
