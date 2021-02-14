package com.xiaoshazhou.service;

import com.xiaoshazhou.mapper.MyUserDetailsServiceMapper;
import com.xiaoshazhou.module.MyUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2021/2/14 16:50
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    /**
     *
     * @param s:代表唯一标识 与前面config中配置一置 此处使用username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //查询用户基本信息
        MyUserDetails myUserDetails = myUserDetailsServiceMapper.findByUsername(s);
        if (myUserDetails == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        //查询用户角色
        List<String> roleCodes = myUserDetailsServiceMapper.findRoleByUsername(s);

        //查询用户角色对应的权限列表
        List<String> authorities = myUserDetailsServiceMapper.findAuthorityByRoleCodes(roleCodes);

        roleCodes = roleCodes.stream()
                .map(rc -> "ROLE_"+rc)
                .collect(Collectors.toList());

        authorities.addAll(roleCodes);

        myUserDetails.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",",authorities)));
        return myUserDetails;
    }
}
