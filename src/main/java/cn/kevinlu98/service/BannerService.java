package cn.kevinlu98.service;

import cn.kevinlu98.mapper.BannerMapper;
import cn.kevinlu98.pojo.Banner;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
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
public class BannerService {

    private final BannerMapper mapper;


    public BannerService(BannerMapper mapper) {
        this.mapper = mapper;
    }
    @CacheEvict(allEntries = true)
    public void order(Integer id, Integer order) {
        mapper.ordered(id, order);
    }


    /**
     * 查询所有的轮播图
     *
     * @return 轮播图列表
     */
    @Cacheable
    public List<Banner> list() {
        return mapper.findAll(Sort.by(Sort.Direction.ASC, "ordered"));
    }

    /**
     * 查询轮播图总数
     *
     * @return 轮播图总数
     */
    public long count() {
        return mapper.count();
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 轮播图详情
     */
    public Banner detail(Integer id) {
        return mapper.findById(id).orElse(null);
    }

    /**
     * 保存操作
     *
     * @param banner 要保存的对象
     */
    @CacheEvict(allEntries = true)
    public void save(Banner banner) {
        mapper.save(banner);
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
