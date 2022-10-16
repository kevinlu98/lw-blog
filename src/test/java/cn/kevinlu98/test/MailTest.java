package cn.kevinlu98.test;

import cn.kevinlu98.Application;
import cn.kevinlu98.common.MailHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/14 19:49
 * Email: kevinlu98@qq.com
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MailTest {
    @Autowired
    private MailHelper helper;

    @Test
    public void testSend(){
        helper.sendMail("kevinlu98@qq.com","测试邮件","<h1>冷文学习者</h1><p>我是一个测试邮件</p>");
    }
}
