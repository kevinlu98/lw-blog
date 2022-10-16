package cn.kevinlu98.test;

import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/16 10:59
 * Email: kevinlu98@qq.com
 * Description:
 */
public class MD5Test {
    @Test
    public void test(){
        String pass = DigestUtils.md5DigestAsHex("yxj6558066".getBytes(StandardCharsets.UTF_8));
        System.out.println(pass);
    }
}
