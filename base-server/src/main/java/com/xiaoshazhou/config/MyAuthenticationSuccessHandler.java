package com.xiaoshazhou.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoshazhou.result.AjaxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.ObjectToStringHttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * TODO
 *
 * 登录成功后的结果响应处理
 * @author zhoujin
 * @date 2021/1/7 20:39
 */
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {


        //如果响应数据格式为JSON
        if (Objects.equals(loginType, "JSON")) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(mapper.writeValueAsString(AjaxResponse.success()));
        } else {
            //如果登录成功后会跳转到登录之前想访问的那个页面
            response.setContentType("text/html;charset=utf-8");
            super.onAuthenticationSuccess(request, response, authentication);

        }
    }
}
