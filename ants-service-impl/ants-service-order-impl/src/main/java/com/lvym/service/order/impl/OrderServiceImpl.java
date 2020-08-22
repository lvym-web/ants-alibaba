package com.lvym.service.order.impl;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.lvym.service.order.feign.MemberServiceFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderServiceImpl {

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @RequestMapping("/getMemberInfoByFeign")
    public String getMemberInfoByFeign(){

        return memberServiceFeign.getMemberInfo(1L);
    }

    private static final String GETORDER_KEY = "getOrder";


//    @RequestMapping("/initFlowQpsRule")
//    public String initFlowQpsRule() {
//        List<FlowRule> rules = new ArrayList<FlowRule>();
//        FlowRule rule1 = new FlowRule();
//        rule1.setResource(GETORDER_KEY);
//        // QPS控制在1以内
//        rule1.setCount(1);
//        // QPS限流
//        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule1.setLimitApp("default");
//        rules.add(rule1);
//        FlowRuleManager.loadRules(rules);
//        return "....限流配置初始化成功..";
//    }


    @RequestMapping("/getOrder")
    public String getOrders() {
        Entry entry = null;
        try {
            entry = SphU.entry(GETORDER_KEY);
            // 执行我们服务需要保护的业务逻辑
            return "getOrder接口";
        } catch (Exception e) {
            e.printStackTrace();
            return "该服务接口已经达到上线!";
        } finally {
            // SphU.entry(xxx) 需要与 entry.exit() 成对出现,否则会导致调用链记录异常
            if (entry != null) {
                entry.exit();
            }
        }

    }

    /**
     * 限流
     * @return
     */
    @SentinelResource(value = GETORDER_KEY,blockHandler ="getOrderQpsException")
    @RequestMapping("/getOrderAnnotation")
    public String getOrderAnnotation() {

       return  "getOrder接口";
    }
    /**
     * 被限流后返回的提示
     *
     * @param e
     * @return
     */
    public String getOrderQpsException(BlockException e) {
        e.printStackTrace();
        return "该接口已经被限流啦!";
    }
    @RequestMapping("/getOrderQPS")
    public String getOrderQPS() {
        System.out.println(Thread.currentThread().getName());

        return "getOrderQPS";
    }

    /**
     * 并发数量处理限流
     * @return
     */
    @SentinelResource(value = "getOrderThrad",blockHandler ="getOrderQpsException")
    @RequestMapping("/getOrderThrad")
    public String getOrderThrad() {
        System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        return "getOrderThrad";
    }

    /**
     * 基于我们的平均响应时间实现降级
     *
     * @return
     */
    @SentinelResource(value = "getOrderDowngradeRtType", fallback = "getOrderDowngradeRtTypeFallback")
    @RequestMapping("/getOrderDowngradeRtType")
    public String getOrderDowngradeRtType() {
        try {
            Thread.sleep(300);
        } catch (Exception e) {
            System.out.println("降级不会走");
        }
        System.out.println("次数");
        return "正常执行我们业务逻辑";
    }

    public String getOrderDowngradeRtTypeFallback() {
        return "执行我们本地的服务降级的方法";
    }

    /**
     * 基于我们错误率/异常实现服务降级
     *
     * @return
     */
    @SentinelResource(value = "getOrderDowngradeErrorType", fallback = "getOrderDowngradeErrorTypeFallback")
    @RequestMapping("/getOrderDowngradeErrorType")
    public String getOrderDowngradeErrorType(int age) {
        int j = 1 / age;
        return "正常执行我们业务逻辑:j" + j;
    }

    public String getOrderDowngradeErrorTypeFallback(int age) {
        return "错误率/异常数太高，展示无法访问该接口";
    }


    /**
     *       持久化
     * @return
     */
    @SentinelResource(value = "getOrderSentinel", blockHandler = "getOrderQpsException")
    @RequestMapping("/getOrderSentinel")
    public String getOrderSentinel() {
        return "getOrderSentinel持久化";
    }


    @SentinelResource(value = "seckill", fallback = "seckillFallback", blockHandler = "seckillBlockHandler")
    @RequestMapping("/seckill")
    public String seckill(Long userId, Long orderId) {
        return "秒杀成功";
    }
    public String seckillFallback(Long userId, Long orderId) {
        return "不走这里";
    }
    public String seckillBlockHandler(Long userId, Long orderId) {
        return "不走这里";
    }

}
