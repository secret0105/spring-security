package com.xiaoshazhou.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoshazhou.result.AjaxResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * TODO
 *
 * 登录失败后的响应结果处理
 * @author zhoujin
 * @date 2021/1/7 20:50
 */
@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${spring.security.loginType}")
    private String loginType;

    private static ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg = "请检查用户名与密码输入是否正确";
        //判断异常是否为SessionAuthenticationException
        if (exception instanceof SessionAuthenticationException) {
            errorMsg = exception.getMessage();
        }

        if (Objects.equals(loginType, "JSON")) {
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(mapper.writeValueAsString(AjaxResponse.inputError(errorMsg)));
        } else {
            response.setContentType("text/html;charset=utf-8");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
