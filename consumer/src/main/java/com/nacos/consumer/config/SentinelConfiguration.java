package com.nacos.consumer.config;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/26
 * 不通过dashboard的方式配置限流
 **/
//@Configuration
//public class SentinelConfiguration {
//
//    @Bean
//    FlowRuleManager flowRuleManager(){
//        List<FlowRule> rules = new ArrayList<FlowRule>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("");
//        // set limit qps to 10
//        rule.setCount(10);
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setLimitApp("default");
//        rules.add(rule);
//        FlowRuleManager.loadRules(rules);
//    }
//
//}
