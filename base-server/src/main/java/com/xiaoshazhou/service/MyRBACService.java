package com.xiaoshazhou.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * TODO
 * 资源鉴权规则:什么角色可以访问什么资源
 * @author zhoujin
 * @date 2021/2/14 20:54
 */
@Component("rbacService")
public class MyRBACService {

    /**
     * 自定义方法 判断用户拥有的资源是否包含请求资源
     * @param request
     * @param authentication
     * @return
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        //获取登录认证的主体信息
        Object principle = authentication.getPrincipal();
        if (principle instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) principle;
            //request.getRequestURI() 获得的是当前请求资源部分 不包括IP和端口
            //封装请求资源对应的地址
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(request.getRequestURI());

            //判断角色拥有的权限资源中是否包含请求资源uri
            return userDetails.getAuthorities().contains(simpleGrantedAuthority);
        }

        return false;
    }
}
