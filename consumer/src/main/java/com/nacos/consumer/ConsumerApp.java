package com.nacos.consumer;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.nacos.demo.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/22
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
//    不能与dubbo兼容
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate(){
//        return new RestTemplate();
//    }

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Slf4j
    @RestController
    static class TestController {

//        @Autowired
//        LoadBalancerClient loadBalancerClient;

//        @Autowired
//        RestTemplate restTemplate;

//        @Autowired
//        Client client;

        @Reference(lazy = true, check = false)
        ITestService testService;

//        @GetMapping("/test")
//        public String test() {
//            // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
//            ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
//            String url = serviceInstance.getUri() + "/hello?name=" + "didi";
//            RestTemplate restTemplate = new RestTemplate();
//            String result = restTemplate.getForObject(url, String.class);
//            return "Invoke : " + url + ", return : " + result;
//        }
//        restTemplate方式
//        @GetMapping("/test2")
//        public String test2(){
//            String result = restTemplate.getForObject("http://alibaba-nacos-discovery-server/hello?name=didi", String.class);
//            return "Return : " + result;
//        }

//        @GetMapping("/test3")
//        public String test3(){
//            return client.hello("didi");
//        }

        @GetMapping("/test4")
        @SentinelResource("test")
        public String test4(){
            return testService.test();
        }
    }

//    @FeignClient("alibaba-nacos-discovery-server")
//    interface Client {
//
//        @GetMapping("/hello")
//        String hello(@RequestParam(name = "name") String name);
//
//    }
}
