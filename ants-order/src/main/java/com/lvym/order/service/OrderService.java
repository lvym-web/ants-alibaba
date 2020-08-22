package com.lvym.order.service;

import com.lvym.order.loadbalance.LoadBalace;
import jdk.internal.org.objectweb.asm.tree.LdcInsnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
public class OrderService {

    @Autowired
    private DiscoveryClient discoveryClient;

//    @RequestMapping("/getOrderToMemberInfo")
//    public Object getOrderToMemberInfo(){
//        List<ServiceInstance> instances = discoveryClient.getInstances("menber-service");
//        System.out.println(">>>>>>>>>>>>>>>>>>"+instances);
//        List<String> services = discoveryClient.getServices();
//        System.out.println(">>>>>>>>>>>services>>>>>>>"+services);
//        return instances.get(0);
//    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalace loadBalace;

    @RequestMapping("/getOrderToMemberInfo")
    public String getOrderToMemberInfo(){
         //获取服务列表
        List<ServiceInstance> instances = discoveryClient.getInstances("menber-service");


       // ServiceInstance serviceInstance = instances.get(0);

        ServiceInstance singleAddres = loadBalace.getSingleAddres(instances);

        String result = restTemplate.getForObject(singleAddres.getUri()+"/getMemberInfo", String.class);

        return "订单调用会员，"+result;
    }

    /**
     *   ribbon  +  @LoadBalanced
     * @return
     */
    @RequestMapping("/getOrderToRibbonMemberInfo")
    public String getOrderToRibbonMemberInfo(){

        String result = restTemplate.getForObject("http://menber-service/getMemberInfo", String.class);

        return "订单调用会员，"+result;
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    /**
     *     LoadBalancerClient  -   @LoadBalanced
     * @return
     */
    @RequestMapping("/getOrderToLoadBalancerClientMemberInfo")
    public Object getOrderToLoadBalancerClientMemberInfo(){

        URI uri = loadBalancerClient.choose("menber-service").getUri();
        String result = restTemplate.getForObject(uri+"/getMemberInfo", String.class);
        return "订单调用会员，"+result;
    }
}
