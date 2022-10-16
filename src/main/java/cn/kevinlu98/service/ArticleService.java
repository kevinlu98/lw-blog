package cn.kevinlu98.service;

import cn.kevinlu98.common.ArticleSearch;
import cn.kevinlu98.common.PageHelper;
import cn.kevinlu98.mapper.ArticleMapper;
import cn.kevinlu98.mapper.CommentMapper;
import cn.kevinlu98.mapper.TagMapper;
import cn.kevinlu98.pojo.Article;
import cn.kevinlu98.pojo.Category;
import cn.kevinlu98.pojo.Tag;
import cn.kevinlu98.utils.UpdateUtil;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:20
 * Email: kevinlu98@qq.com
 * Description:
 */
@CacheConfig(cacheNames = {"blog-cache"})
@Service
public class ArticleService {

    private final ArticleMapper mapper;

    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;


    public ArticleService(ArticleMapper mapper, CommentMapper commentMapper, TagMapper tagMapper) {
        this.mapper = mapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }


    /**
     * 查询所有的文章
     *
     * @return 文章列表
     */
    public List<Article> list() {
        return mapper.findAll();
    }

    /**
     * 查询文章总数
     *
     * @return 文章总数
     */
    public long count() {
        return mapper.count();
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 文章详情
     */
    public Article detail(Integer id) {
        Article article = mapper.findById(id).orElse(null);
        if (Objects.isNull(article)) {
            return null;
        }
        article.setCommentCount(commentMapper.countByArticle(article));
        return article;
    }

    /**
     * 保存操作
     *
     * @param article 要保存的对象
     */
    @CacheEvict(allEntries = true)
    @Transactional
    public void save(Article article) {
        article.setUpdated(new Date());
        if (Objects.isNull(article.getType())) {
            article.setType(Article.TYPE_ARTICLE);
        }
        if (Objects.isNull(article.getStatus())) {
            article.setStatus(Article.STATUS_PUBLISH);
        }
        if (Objects.isNull(article.getAllowComment())) {
            article.setAllowComment(Article.COMMENT_DISABLE);
        }
        // 将数据库中不存在的标签先执行插入操作
        article.setTags(article.getTags().stream().peek(x -> {
            if (Objects.isNull(x.getId())) {
                x.setId(tagMapper.save(x).getId());
            }
        }).collect(Collectors.toList()));
        if (Objects.isNull(article.getId())) {
            article.setCreated(new Date());
            article.setViews(0);
            mapper.save(article);
        } else {
            Article one = detail(article.getId());
            UpdateUtil.copyNullProperties(article, one);
            mapper.save(one);
        }
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

    /**
     * 多条件分布查询
     *
     * @param search 查询参数
     * @return 分页对象
     */

    public PageHelper<Article> search(ArticleSearch search) {
        //构建分页参数
        Pageable pageable = PageRequest.of(search.getPageNum() - 1, search.getPageSize(), Sort.by(Sort.Direction.DESC, "created"));
        Page<Article> articlePage = mapper.findAll((Specification<Article>) (root, query, builder) -> {
            List<Predicate> predicateAnd = new ArrayList<>();
            if (Objects.nonNull(search.getCid())) {
                predicateAnd.add(builder.equal(root.get("category"), Category.builder().id(search.getCid()).build()));
            }
            if (Objects.nonNull(search.getStatus())) {
                predicateAnd.add(builder.equal(root.get("status"), search.getStatus()));
            }
            if (Objects.nonNull(search.getType())) {
                predicateAnd.add(builder.equal(root.get("type"), search.getType()));
            }
            if (!StringUtils.isEmptyOrWhitespace(search.getTitle())) {
                predicateAnd.add(builder.like(root.get("title"), "%" + search.getTitle() + "%"));
            }
            if (Objects.nonNull(search.getTid())) {
                ListJoin<Article, Tag> join = root.join(root.getModel().getList("tags", Tag.class), JoinType.LEFT);
                predicateAnd.add(builder.equal(join.get("id"), search.getTid()));
            }
            List<Predicate> predicateOr = new ArrayList<>();
            if (!StringUtils.isEmptyOrWhitespace(search.getKeyword())) {
                // todo 如果可以话后面给大家 出一期进阶课，把这里的搜索换顾EleasticSearch
                predicateOr.add(builder.like(root.get("title"), "%" + search.getKeyword() + "%"));
                predicateOr.add(builder.like(root.get("content"), "%" + search.getKeyword() + "%"));
            }
            if (CollectionUtils.isEmpty(predicateOr)) {
                return builder.and(predicateAnd.toArray(new Predicate[predicateAnd.size()]));
            }
            return query.where(builder.and(predicateAnd.toArray(new Predicate[predicateAnd.size()])), builder.or(predicateOr.toArray(new Predicate[predicateOr.size()]))).getRestriction();
        }, pageable);
        return PageHelper.<Article>builder()
                .rows(articlePage.getContent().stream().peek(x -> x.setCommentCount(commentMapper.countByArticle(x))).collect(Collectors.toList()))
                .total(articlePage.getTotalElements())
                .current(search.getPageNum())
                .totalPage(articlePage.getTotalPages())
                .build();
    }

    @Cacheable
    public List<Article> hotList(int length) {
        return mapper.hotList(length);
    }

    public void viewArticle(Integer id) {
        mapper.viewsArticle(id);
    }
}
