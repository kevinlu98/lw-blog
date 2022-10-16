package cn.kevinlu98.test;

import cn.kevinlu98.Application;
import cn.kevinlu98.common.DefaultImage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/11 11:57
 * Email: kevinlu98@qq.com
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageTest {
    @Autowired
    private DefaultImage defaultImage;


    @Test
    public void testCover(){
        System.out.println(defaultImage.cover(""));
        System.out.println(defaultImage.cover("111"));
    }

    @Test
    public void testAvatar(){
        System.out.println(defaultImage.avatar("1628048198@qq.com"));
        System.out.println(defaultImage.avatar("1628048198@qq11.com"));
    }
}
