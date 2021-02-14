package com.xiaoshazhou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * TODO
 *
 * httpbasic 登录认证
 * @author zhoujin
 * @date 2020/11/30 21:58
 */
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();//所有请求都要认证后才能访问
    }
}
