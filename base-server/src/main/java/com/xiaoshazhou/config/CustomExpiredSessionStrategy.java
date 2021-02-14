package com.xiaoshazhou.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.javafx.collections.MappingChange;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2021/1/31 21:13
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {


    //页面跳转处理逻辑，直接跳转网页
//        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    ObjectMapper mapper = new ObjectMapper();

    //传递json数据
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        //redirectStrategy.sendRedirect(event.getRequest(),event.getResponse(),"/跳转的页面");
        Map<String, Object> map = new HashMap<>();
        map.put("code", 403);
        map.put("msg", "你的登录已超时或者已经在另一台机器登录，您被迫下线" + event.getSessionInformation().getLastRequest());
        String json = mapper.writeValueAsString(map);
        event.getResponse().setContentType("application/json; charset=UTF-8");
        event.getResponse().getWriter().write(json);

    }
}
