package cn.kevinlu98.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/11 11:54
 * Email: kevinlu98@qq.com
 * Description:
 */
@Component
@Data
@ConfigurationProperties(prefix = "default-image")
public class DefaultImage {

    //默认文章封面
    private List<String> images;
    //默认评论头像
    private List<String> avatars;

    public String cover(String imgUrl) {
        if (StringUtils.isEmptyOrWhitespace(imgUrl)) {
            return images.get((int) (Math.random() * images.size()));
        }
        return imgUrl;
    }

    public String avatar(String mail) {
        Pattern pattern = Pattern.compile("(\\d{5,10})@qq.com");
        Matcher matcher = pattern.matcher(mail);
        if (matcher.find()) {
            String qq = matcher.group(1);
            return String.format("https://q1.qlogo.cn/g?b=qq&nk=%s&s=100", qq);
        }
        return avatars.get((int) (Math.random() * avatars.size()));
    }

}
