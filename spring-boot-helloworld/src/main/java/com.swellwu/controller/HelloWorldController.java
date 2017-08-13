package com.swellwu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author swellwu
 * @create 2017-08-12-17:15
 */
@RestController
public class HelloWorldController {

    @GetMapping("/hello-world")
    public String helloWorld(){
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");// 获取用户名
        return "hello,"+userName;
    }
}
