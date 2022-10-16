package cn.kevinlu98.config;

import cn.kevinlu98.pojo.Category;
import cn.kevinlu98.service.CategoryService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryService categoryService;

    public ContextRefreshedListener(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (categoryService.count() == 0) {
            categoryService.save(Category.builder().name("默认分类").summary("这是一个默认分类").build());
        }
    }
}