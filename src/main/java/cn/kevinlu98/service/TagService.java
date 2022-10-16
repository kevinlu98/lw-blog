package cn.kevinlu98.service;

import cn.kevinlu98.mapper.TagMapper;
import cn.kevinlu98.pojo.Tag;
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
@CacheConfig(cacheNames = {"blog-cache"})
@Service
public class TagService {

    private final TagMapper mapper;


    public TagService(TagMapper mapper) {
        this.mapper = mapper;
    }


    /**
     * 查询所有的标签
     *
     * @return 标签列表
     */
    public List<Tag> list() {
        return mapper.findAll();
    }

    /**
     * 查询标签总数
     *
     * @return 标签总数
     */
    public long count() {
        return mapper.count();
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 标签详情
     */
    public Tag detail(Integer id) {
        return mapper.findById(id).orElse(null);
    }

    /**
     * 保存操作
     *
     * @param tag 要保存的对象
     */
    @CacheEvict(allEntries = true)
    public void save(Tag tag) {
        mapper.save(tag);
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
    public List<Tag> list(int length) {
        return mapper.findRandom(length);
    }

    @Cacheable
    public List<Tag> show() {
        return mapper.findAll().stream().peek(tag -> tag.setArticleCount(mapper.articleCountByTid(tag.getId()))).filter(tag -> tag.getArticleCount()>0).collect(Collectors.toList());
    }
}
