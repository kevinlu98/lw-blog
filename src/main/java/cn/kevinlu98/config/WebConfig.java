package cn.kevinlu98.config;

import cn.kevinlu98.common.LRUCache;
import cn.kevinlu98.intercepto.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 16:17
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.base-dir}")
    private String baseDir;

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 创建static目录到/static/的路由
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        File dir = new File(baseDir);
        if (!dir.isDirectory()) {
            // 创建目录
            dir.mkdirs();
        }
        String absolutePath = dir.getAbsolutePath().replace("./", "") + "/";
        log.info("absolutePath:{}", absolutePath);
        //  一定要在绝对路径前加上file://表明它是一个本地目录
        registry.addResourceHandler("/upload/**").addResourceLocations("file://" + absolutePath);
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/admin/**")
//                .excludePathPatterns("/admin/login")
//                .excludePathPatterns("/admin/login.html")
//                .excludePathPatterns("/admin/logout");
//    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"), new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.html"));
    }

    @Bean
    public LRUCache lruCache() {
        return new LRUCache(10);
    }
}
