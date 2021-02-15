package com.xiaoshazhou.controller;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xiaoshazhou.module.KaptchaVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * TODO
 *
 * @author zhoujin
 * @date 2021/2/15 14:06
 */
@RestController
public class CaptchaController {

    @Resource
    private DefaultKaptcha captchaProducer;

    @RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
    public void kaptchaCode(HttpSession session, HttpServletResponse response) throws IOException {

        //固定设置
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "create_date-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //获取验证码
        String capText = captchaProducer.createText();
        session.setAttribute("captcha_key", new KaptchaVO(capText, 1 * 60));


        try(ServletOutputStream out = response.getOutputStream()){
            //生成图片
            BufferedImage image = captchaProducer.createImage(capText);
            ImageIO.write(image, "jpg", out);
            out.flush();
            
        }
    }
}
