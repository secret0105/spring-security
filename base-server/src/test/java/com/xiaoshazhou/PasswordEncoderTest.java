package com.xiaoshazhou;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2021/1/6 20:52
 */

public class PasswordEncoderTest {

    @Test
    public void bCryptPasswordTest() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //定义初始密码
        String rawPassword = "123456";
        //加密
        String encoderPassword = passwordEncoder.encode(rawPassword);
        //判断是否匹配
        System.out.println(rawPassword + "是否匹配" + encoderPassword + ":" + passwordEncoder.matches(rawPassword, encoderPassword));
        System.out.println("654321是否匹配" + encoderPassword + ":" + passwordEncoder.matches("654321", encoderPassword));


    }
}
