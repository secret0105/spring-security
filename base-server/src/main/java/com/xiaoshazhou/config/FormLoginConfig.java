package com.xiaoshazhou.config;

import com.xiaoshazhou.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PipedReader;

/**
 * TODO
 * <p>
 * formLogin登录认证
 *
 * @author zhoujin
 * @date 2021/1/6 21:24
 */
@Configuration
public class FormLoginConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    /**
     * 登录逻辑认证：登录url  跳转url  接收登录参数等
     * <p>
     * 资源访问控制：什么用户、什么角色可以访问什么资源
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//关闭跨域攻击
                .formLogin()//开启formLogin认证
                .loginPage("/login.html")//设置登录页面
                .loginProcessingUrl("/login")//处理认证请求的路径，也就是html表单上action的值
                .usernameParameter("username")//请求表单中输入框name值
                .passwordParameter("password")
//                .defaultSuccessUrl("/")//登录成功后默认跳转到首页
//                .failureForwardUrl("/login.html")
                .successHandler(myAuthenticationSuccessHandler)//使用我们自定义的响应配置
                .failureHandler(myAuthenticationFailureHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login.html", "login").permitAll()//不需要登录就能访问的页面
                //动态鉴权
                .anyRequest().access("@rbacService.hasPermission(request,authentication)")
                //注释的为静态方式，现使用动态方式
                /*.antMatchers("/", "/bizone", "/biztwo")
                .hasAnyAuthority("ROLE_user", "ROLE_admin")*/
                /*.antMatchers("/syslog", "/sysuser")
                .hasAnyRole("admin")*/
                /*.antMatchers("/syslog")//等价于上面
                .hasAnyAuthority("/syslog")
                .antMatchers("/sysuser")
                .hasAnyAuthority("/sysuser")*/
                /*.antMatchers("/syslog")//等价于上面
                .hasAuthority("/syslog")
                .antMatchers("/sysuser")
                .hasAuthority("/sysuser")
                .anyRequest()
                .authenticated()*/
                .and()
                //设置session多端登录策略
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation()
                .migrateSession()
                //只允许一个账号进行登录
                .maximumSessions(1)
                //如果为true 则不能在另一个浏览器上登录了
                //如果为false 则可以将已经登录的账号踢下线
                .maxSessionsPreventsLogin(false)
                //自定义session多端控制策略类
                .expiredSessionStrategy(new CustomExpiredSessionStrategy());
    }

    /**
     * 配置用户角色权限：某个用户拥有什么角色、拥有什么权限
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth./*inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("user")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
//                .authorities("sys:log","sys:user")
                .roles("admin")*/
                userDetailsService(myUserDetailsService)
//                .and()
                .passwordEncoder(passwordEncoder());//配置bCrypt加密
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 放行静态资源
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/font/**", "/img/**");
    }
}
