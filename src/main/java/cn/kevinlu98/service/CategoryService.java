package cn.kevinlu98.service;

import cn.kevinlu98.mapper.ArticleMapper;
import cn.kevinlu98.mapper.CategoryMapper;
import cn.kevinlu98.pojo.Category;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:20
 * Email: kevinlu98@qq.com
 * Description:
 */
@Service
@CacheConfig(cacheNames = {"blog-cache"})
public class CategoryService {

    private final CategoryMapper mapper;

    private final ArticleMapper articleMapper;


    public CategoryService(CategoryMapper mapper, ArticleMapper articleMapper) {
        this.mapper = mapper;
        this.articleMapper = articleMapper;
    }


    /**
     * 查询所有的分类
     *
     * @return 分类列表
     */
    public List<Category> list() {
        return mapper.findAll();
    }

    /**
     * 查询分类总数
     *
     * @return 分类总数
     */
    public long count() {
        return mapper.count();
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 分类详情
     */
    public Category detail(Integer id) {
        return mapper.findById(id).orElse(null);
    }

    /**
     * 保存操作
     *
     * @param category 要保存的对象
     */
    @CacheEvict(allEntries = true)
    public void save(Category category) {
        mapper.save(category);
    }

    /**
     * 根据id从数据库表中删除数据
     *
     * @param id 主键
     */
    @CacheEvict(allEntries = true)
    public void delete(Integer id) {
        mapper.deleteById(id);
    }

    @Cacheable
    public List<Category> show() {
        return mapper.findAll().stream().peek(category -> {
            category.setArticleCount(articleMapper.countByCategory(category));
            category.setLastUpdated(articleMapper.lastUpdated(category.getId()));
        }).collect(Collectors.toList());
    }
}
