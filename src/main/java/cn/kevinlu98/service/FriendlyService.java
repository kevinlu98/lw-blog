package cn.kevinlu98.service;

import cn.kevinlu98.mapper.FriendlyMapper;
import cn.kevinlu98.pojo.Friendly;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:20
 * Email: kevinlu98@qq.com
 * Description:
 */
@CacheConfig(cacheNames = {"blog-cache"})
@Service
public class FriendlyService {

    private final FriendlyMapper mapper;


    public FriendlyService(FriendlyMapper mapper) {
        this.mapper = mapper;
    }


    /**
     * 查询所有的友情链接
     *
     * @return 友情链接列表
     */
    @Cacheable
    public List<Friendly> list() {
        return mapper.findAll();
    }

    /**
     * 查询友情链接总数
     *
     * @return 友情链接总数
     */
    public long count() {
        return mapper.count();
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 友情链接详情
     */
    public Friendly detail(Integer id) {
        return mapper.findById(id).orElse(null);
    }

    /**
     * 保存操作
     *
     * @param friendly 要保存的对象
     */

    @CacheEvict(allEntries = true)
    public void save(Friendly friendly) {
        mapper.save(friendly);
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
}
