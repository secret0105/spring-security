package com.xiaoshazhou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2020/11/30 22:32
 */
@Controller
public class BizController {

    //使用formLogin
    /*@PostMapping("/login")
    public String index(String username, String password) {
        return "index";
    }*/

    @GetMapping("/syslog")
    public String syslog() {
        return "syslog";
    }

    @GetMapping("/sysuser")
    public String sysuser() {
        return "sysuser";
    }

    @GetMapping("/bizone")
    public String updateOrder() {
        return "bizone";
    }

    @GetMapping("/biztwo")
    public String deleteOrder() {

        return "biztwo";
    }
}
