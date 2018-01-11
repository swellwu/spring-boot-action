package com.swellwu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author swellwu
 * @create 2017-08-14-21:14
 */
//配置Druid的监控页面需要用到的注解
@ServletComponentScan
@SpringBootApplication
@MapperScan("com.swellwu.mapper")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
