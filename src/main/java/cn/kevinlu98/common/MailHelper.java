package cn.kevinlu98.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/14 19:44
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@Component
public class MailHelper {

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.enable}")
    private boolean enable;
    private final JavaMailSender javaMailSender;

    public MailHelper(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * 发送邮件
     *
     * @param send    收件人
     * @param subject 主题
     * @param text    内容
     */
    @Async
    public void sendMail(String send, String subject, String text) {
        if (!enable) return;
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setSentDate(new Date()); //设置发件时间
            helper.setFrom(from); //发件人
            helper.setTo(send); //设置收件人
            helper.setSubject(subject); //设置标签
            helper.setText(text, true); //设置内容
            javaMailSender.send(message); //发邮件
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
    }
}
