package com.xiaoshazhou.module;

import org.omg.CORBA.PRIVATE_MEMBER;

import java.time.LocalDateTime;

/**
 * TODO
 *
 * 验证码加上过期时间的封装
 * @author zhoujin
 * @date 2021/2/15 14:10
 */
public class KaptchaVO {

    private String capText;

    private LocalDateTime expiredTime;

    public KaptchaVO(String capText, int expired) {
        this.capText = capText;
        this.expiredTime = LocalDateTime.now().plusSeconds(expired);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredTime);
    }

    public String getCapText() {
        return capText;
    }
}
