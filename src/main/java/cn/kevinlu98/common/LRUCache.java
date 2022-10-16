package cn.kevinlu98.common;


import java.util.ArrayList;
import java.util.LinkedHashSet;

import java.util.List;
import java.util.Set;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/12 18:06
 * Email: kevinlu98@qq.com
 * Description:
 */
public class LRUCache {
    private final Set<String> cache = new LinkedHashSet<>();

    private final int limit;

    public LRUCache(int limit) {
        this.limit = limit;
    }

    public int size() {
        return cache.size();
    }

    public List<String> list() {
        return new ArrayList<>(cache);
    }

    public void add(String keyword) {
        while (cache.size() >= limit) {
            cache.remove(cache.stream().findFirst().orElse(null));
        }
        cache.remove(keyword);
        cache.add(keyword);
    }

}
