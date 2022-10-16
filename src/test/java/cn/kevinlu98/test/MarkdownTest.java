package cn.kevinlu98.test;

import cn.kevinlu98.utils.MarkdownUtil;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/13 14:11
 * Email: kevinlu98@qq.com
 * Description:
 */
public class MarkdownTest {
    @Test
    public void testParse() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("/Users/lengwen/Documents/SptringBoot博客项目/（三）分类相关功能设计（下）.md"));
        String line;
        StringBuilder content = new StringBuilder();
        while ((line = br.readLine()) != null) {
            content.append(line).append("\n");
        }
        System.out.println(MarkdownUtil.parse(content.toString()));
    }


    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString().replace("-", ""));
    }
}
