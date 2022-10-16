package cn.kevinlu98.mapper;

import cn.kevinlu98.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:04
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface TagMapper extends JpaRepository<Tag, Integer> {

    @Query(value = "select * from blog_tag order by rand() limit ?1", nativeQuery = true)
    List<Tag> findRandom(int length);

    @Query(value = "select count(1) from blog_article_tags where tags_id = ?1", nativeQuery = true)
    Long articleCountByTid(Integer id);
}
