package cn.kevinlu98.service;

import cn.kevinlu98.mapper.NavigationMapper;
import cn.kevinlu98.pojo.Navigation;
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
public class NavigationService {

    private final NavigationMapper mapper;


    public NavigationService(NavigationMapper mapper) {
        this.mapper = mapper;
    }


    /**
     * 查询所有的导航条
     *
     * @return 导航条列表
     */
    public List<Navigation> list() {
        return mapper.findAll(Sort.by(Sort.Direction.ASC, "ordered"));
    }

    /**
     * 查询导航条总数
     *
     * @return 导航条总数
     */
    public long count() {
        return mapper.count();
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 导航条详情
     */
    public Navigation detail(Integer id) {
        return mapper.findById(id).orElse(null);
    }

    /**
     * 保存操作
     *
     * @param navigation 要保存的对象
     */
    @CacheEvict(allEntries = true)
    public void save(Navigation navigation) {
        mapper.save(navigation);
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
    @CacheEvict(allEntries = true)
    public void order(Integer id, Integer order) {
        mapper.ordered(id, order);
    }

    @Cacheable
    public List<Navigation> show() {
        return mapper.findAllByEnableOrderByOrderedAsc(true);
    }
}
