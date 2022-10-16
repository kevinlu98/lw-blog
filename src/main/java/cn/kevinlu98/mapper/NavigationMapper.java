package cn.kevinlu98.mapper;

import cn.kevinlu98.pojo.Navigation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:04
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface NavigationMapper extends JpaRepository<Navigation, Integer> {

    @Transactional
    @Modifying
    @Query("update Navigation set ordered = ?2 where  id = ?1")
    void ordered(Integer id, Integer order);

    List<Navigation> findAllByEnableOrderByOrderedAsc(Boolean enable);
}
