package cn.kevinlu98.pojo;

import cn.kevinlu98.utils.MarkdownUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/7 15:45
 * Email: kevinlu98@qq.com
 * Description:
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "blog_article")
public class Article implements Serializable {

    public static final String VIEW_PREFIX = "LW_ARTICLE_VIEWS_ID_";

    // 文章
    public static final int TYPE_ARTICLE = 1;
    // 页面
    public static final int TYPE_PAGE = 2;
    //发布
    public static final int STATUS_PUBLISH = 1;
    //草稿
    public static final int STATUS_DRAFT = 2;
    //允许评论
    public static final int COMMENT_ENABLE = 1;
    //禁止评论
    public static final int COMMENT_DISABLE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //主键


    private String title; //标题

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    private String content; //文章内容

    private String cover; //缩略图

    private Integer type; //类型

    private Integer status; //状态

    private Integer allowComment; //允许评论

    private Integer views; //浏览量

    @Temporal(TemporalType.TIMESTAMP)
    private Date created; //创建时间

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated; //更新时间

    @ManyToMany
    @ToString.Exclude
    private List<Tag> tags;

    @ManyToOne
    private Category category;

    @Transient
    private long commentCount;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Article article = (Article) o;
        return id != null && Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public String summary() {
        String summary = this.content.replaceAll("[^\\u4E00-\\u9FA5a-zA-Z]", "");
        return summary.substring(0, Math.min(300, summary.length()));
    }

    public String showHtml() {
        return MarkdownUtil.parse(this.content);
    }
}
