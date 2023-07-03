package com.xjtuhub.controller;

import com.google.code.kaptcha.Producer;
import com.xjtuhub.common.result.JSONResult;
import com.xjtuhub.common.result.ResponseStatusEnum;
import com.xjtuhub.common.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/*
    验证码
*/
@Api(tags = "验证码功能")
@RestController
@RequestMapping("/captcha")
public class VerificationCodeController {

    @Resource(name="captchaProducer")
    private Producer captchaProducer;
    // Springboot提供的针对redis操作字符串的便捷工具对象
    private static RedisOperator redisOperator;

    @Autowired
    public void setRedisOperator(RedisOperator redisOperator) {
        VerificationCodeController.redisOperator = redisOperator;
    }
    /**
     * 验证码生成接口
     */
    @ApiOperation(value = "生成验证码")
    @GetMapping("/captchaImage")
    public JSONResult getCaptchaImage() {
        ByteArrayOutputStream baos = null;
        try {
            String code = captchaProducer.createText();    // 验证码4位字符
            BufferedImage bi = captchaProducer.createImage(code); // 验证码图片

            baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", baos);
            baos.flush();
            String imgEncode = Base64.encodeBase64String(baos.toByteArray()); // 验证码图片的Base64编码字符串

            String key = UUID.randomUUID().toString().replace("-", ""); // 存储验证码的key
            redisOperator.set(key, code, 1800); // 将验证码的key和value存入redis中

            Map<String, String> map = new HashMap<>();
            map.put("imgUrl", imgEncode);
            map.put("imgKey", key);
            return JSONResult.ok(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSONResult.responseCustom(ResponseStatusEnum.CODE_GENERATE_ERROR);
    }

    /**
     * 验证码后台验证
     */
    /*
    @GetMapping("/verify-code")
    public JSONResult verifyCode(String verificationCode) {
        String serverCode = stringRedisTemplate.opsForValue().get("");
        if(verificationCode.equals(serverCode)){
            return JSONResult.ok("验证码正确");
        }else{
            return JSONResult.responseCustom(ResponseStatusEnum.CODE_ERROR);
        }
    }
     */

}
