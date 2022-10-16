package cn.kevinlu98.mapper;

import cn.kevinlu98.pojo.Article;
import cn.kevinlu98.pojo.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:04
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface ArticleMapper extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {


    @Query(value = "select * from blog_article where type = 1 and status = 1 order by views desc limit ?1", nativeQuery = true)
    List<Article> hotList(int length);

    Long countByCategory(Category category);

    @Query(value = "select updated from blog_article where category_id=?1 order by updated desc limit 1", nativeQuery = true)
    Date lastUpdated(Integer id);

    @Transactional
    @Modifying
    @Query(value = "update blog_article set views=views+1 where  id=?1", nativeQuery = true)
    void viewsArticle(Integer id);

}
