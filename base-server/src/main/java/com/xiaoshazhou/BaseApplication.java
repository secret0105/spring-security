package com.xiaoshazhou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2020/11/30 21:57
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.xiaoshazhou.mapper"})
public class BaseApplication{
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
