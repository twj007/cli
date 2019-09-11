package com.front.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/11
 **/
@RestController
public class BaseController {

    @GetMapping("/")
    public ResponseEntity index(){
        return ResponseEntity.ok("ok, you have permission");
    }
}
