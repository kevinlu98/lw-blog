package cn.kevinlu98.mapper;

import cn.kevinlu98.pojo.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:04
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface BannerMapper extends JpaRepository<Banner, Integer> {

    @Transactional
    @Modifying
    @Query("update Banner set ordered = ?2 where  id = ?1")
    void ordered(Integer id, Integer order);
}
