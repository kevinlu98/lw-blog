package cn.kevinlu98.common;

import cn.kevinlu98.pojo.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/9 21:13
 * Email: kevinlu98@qq.com
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSearch {
    //分类id
    private Integer cid;
    //标签的id
    private Integer tid;
    //状态
    private Integer status;
    //标题
    private String title;
    //关键字
    private String keyword;
    //文章类型
    private Integer type;
    //页码
    private Integer pageNum;
    //页大小
    private Integer pageSize;

    public static ArticleSearch indexShow(int pageNum, int pageSize) {
        pageNum = Math.max(pageNum, 1);
        pageSize = Math.max(pageSize, 1);
        return ArticleSearch.builder().pageSize(pageSize).pageNum(pageNum).status(Article.STATUS_PUBLISH).type(Article.TYPE_ARTICLE).build();
    }
}
