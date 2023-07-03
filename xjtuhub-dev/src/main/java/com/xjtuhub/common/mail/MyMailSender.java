package com.xjtuhub.common.mail;

import cn.hutool.core.util.IdUtil;
import com.xjtuhub.common.utils.RedisOperator;
import com.xjtuhub.common.exception.MailException;
import com.xjtuhub.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Component
public class MyMailSender {
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;


    private String prefixIp = "localhost:5173/activate/";
    public void SendMail(Role role){


        //生成用户唯一标识uuid，redis设置过期时间30分钟
        String uuid = IdUtil.simpleUUID();
        redisOperator.set(uuid,role.getRoleId(),1800);
        try {
            //获取邮箱接受者，根据你自己逻辑获取
            String email = role.getEmail();
            Context context = new Context();
            context.setVariable("email",email);
            context.setVariable("activeUrl",prefixIp+uuid);
            //调用邮件发送模板页面，渲染email接收者、activeUrl激活链接
            String emailTemplate = templateEngine.process("emailTemplate", context);
            //创建可以HTML发送的邮件
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("XJTUhub<"+from+">");
            helper.setTo(email);
            helper.setSubject("XJTUhub激活邮件");
            //true代表使用html渲染
            helper.setText(emailTemplate,true);
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new MailException("发送邮件失败");
        }
    }


}
