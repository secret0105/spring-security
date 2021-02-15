package com.xiaoshazhou;

import com.xiaoshazhou.config.MyAuthenticationFailureHandler;
import com.xiaoshazhou.module.KaptchaVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * TODO
 *
 * 自定义验证过滤器 用来验证用户输入的验证码信息
 * @author zhoujin
 * @date 2021/2/15 21:08
 */
@Component
public class MyCaptchaFilter extends OncePerRequestFilter {

    @Resource
    MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        //判断是否为登录请求 并且请求方式为post
        if (StringUtils.equals("/login", request.getRequestURI()) &&
                StringUtils.equalsIgnoreCase("post",request.getMethod())) {

            //开始验证

            try {
                validateCode(new ServletWebRequest(request));
            } catch (AuthenticationException e) {
                myAuthenticationFailureHandler.onAuthenticationFailure(request,response,e);
                return;
            }
        }

        filterChain.doFilter(request,response);
    }

    /**
     * 自定义验证方法来检查验证码
     * @param request
     */
    private void validateCode(ServletWebRequest request) throws ServletRequestBindingException {
        HttpSession session = request.getRequest().getSession();
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "captchaCode");

        //校验验证码
        if (StringUtils.isEmpty(codeInRequest)) {
            throw new SessionAuthenticationException("验证码不能为空");
        }
        //获取Session中的验证码
        KaptchaVO codeInSession = (KaptchaVO) session.getAttribute("captcha_key");
        if (Objects.isNull(codeInSession)) {
            throw new SessionAuthenticationException("验证码不存在");
        }
        //验证码是否过期
        if (codeInSession.isExpired()) {
            throw new SessionAuthenticationException("验证码已过期");
        }
        //匹配验证码
        if (!StringUtils.equals(codeInRequest, codeInSession.getCapText())) {
            throw new SessionAuthenticationException("验证码不匹配");
        }
    }
}
