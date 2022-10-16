package cn.kevinlu98;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 15:57
 * Email: kevinlu98@qq.com
 * Description:
 */
@EnableAsync
@EnableCaching
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}