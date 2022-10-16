package cn.kevinlu98.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.StringUtils;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/11 16:37
 * Email: kevinlu98@qq.com
 * Description:
 */
@Configuration
public class CacheConfig extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> new BlogCacheKey(target.getClass().getName(),method.getName(),params);
    }

    static class BlogCacheKey implements Serializable {
        private final String className;
        private final String methodName;
        private final Object[] params;

        private final int hashCode;

        public BlogCacheKey(String className, String methodName, Object[] params) {
            this.className = className;
            this.methodName = methodName;
            this.params = params;
            String sign = className + "_" + methodName + "_" + Arrays.deepHashCode(params);
            this.hashCode = sign.hashCode();
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (!(obj instanceof BlogCacheKey)) return false;
            BlogCacheKey other = (BlogCacheKey) obj;
            if (this.hashCode == other.hashCode) return true;
            return StringUtils.equals(this.className, other.className)
                    && StringUtils.equals(this.methodName, other.methodName)
                    && Arrays.deepEquals(this.params, other.params);
        }
    }
}
