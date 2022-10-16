package cn.kevinlu98.service;

import cn.kevinlu98.common.MailHelper;
import cn.kevinlu98.common.PageHelper;
import cn.kevinlu98.common.WebSite;
import cn.kevinlu98.mapper.ArticleMapper;
import cn.kevinlu98.mapper.CommentMapper;
import cn.kevinlu98.pojo.Article;
import cn.kevinlu98.pojo.Comment;
import cn.kevinlu98.utils.UpdateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:20
 * Email: kevinlu98@qq.com
 * Description:
 */
@Service
public class CommentService {

    private final CommentMapper mapper;
    private final ArticleMapper articleMapper;

    private final MailHelper helper;

    private final WebSite webSite;


    public CommentService(CommentMapper mapper, ArticleMapper articleMapper, MailHelper helper, WebSite webSite) {
        this.mapper = mapper;
        this.articleMapper = articleMapper;
        this.helper = helper;
        this.webSite = webSite;
    }


    /**
     * 查询所有的评论
     *
     * @return 评论列表
     */
    public PageHelper<Comment> list(Boolean view, int pn, int ps) {
        Pageable pageable = PageRequest.of(pn - 1, ps, Sort.by(Sort.Direction.DESC, "created"));
        Page<Comment> page = Objects.isNull(view) ? mapper.findAll(pageable) : mapper.findAllByView(view, pageable);
        return PageHelper.<Comment>builder()
                .rows(page.getContent())
                .total(page.getTotalElements())
                .build();
    }

    /**
     * 查询评论总数
     *
     * @return 评论总数
     */
    public long count(Boolean view) {
        return Objects.isNull(view)?mapper.count():mapper.countByView(view);
    }

    /**
     * id查询资源详情
     *
     * @param id 主键
     * @return 评论详情
     */
    public Comment detail(Integer id) {
        return mapper.findById(id).orElse(null);
    }

    /**
     * 保存操作
     *
     * @param comment 要保存的对象
     */
    public void save(Comment comment) {
        //新评论
        if (Objects.isNull(comment.getId())) {
            sendMailToWebsite(comment.getArticle().getId());
            comment.setCreated(new Date());
            comment.setView(false);
            if (comment.getPid() != 0) {
                sendMailToComment(comment.getArticle().getId(), comment.getPid(), comment.getContent());
                Comment parent = detail(comment.getPid());
                comment.setContent("@" + parent.getNickname() + ": " + comment.getContent());
                comment.setPid(findParent(comment.getPid()));
            }
            mapper.save(comment);
        } else {
            Comment one = detail(comment.getId());
            UpdateUtil.copyNullProperties(comment, one);
            mapper.save(one);
        }

    }

    public void sendMailToComment(Integer id, Integer cid, String replay) {
        Article article = articleMapper.findById(id).orElse(null);
        if (Objects.isNull(article)) return;
        Comment comment = mapper.findById(cid).orElse(null);
        if (Objects.isNull(comment)) return;
        String content = "<p>\n" +
                "    你在<a href=\"" + webSite.getUrl() + "\">" + webSite.getTitle() +
                "</a>对 <a href=\"" + webSite.getUrl() + "/" + article.getId() + ".html\">" + article.getTitle() +
                "</a>文章的评论收到了新回复，回复内容如下：\n" +
                "</p>\n" +
                "<p>\n" +
                replay +
                "</p>\n" +
                "<p style=\"text-align:right ;\">\n" +
                "   一一发件人：" + webSite.getTitle() +
                "</p>\n" +
                "<p style=\"text-align:right ;\">\n" +
                "时间：" + new Date() +
                "</p>\n" +
                "<p style=\"text-align:right ;\">\n" +
                "    此邮件是由" + webSite.getTitle() + "自动发送，请勿回复 \n" +
                " </p>";
        helper.sendMail(comment.getEmail(), webSite.getTitle() + "收到新回复", content);
    }


    public void sendMailToWebsite(Integer id) {
        Article article = articleMapper.findById(id).orElse(null);
        if (Objects.isNull(article)) return;
        String content = "<p>\n" + "    你的文章 <a href=\"" + webSite.getUrl() + "/" + id + ".html\">" + article.getTitle() + "</a>收到了新评论，点些查看\n" + "</p>\n" + "<p style=\"text-align:right ;\">\n" + "时间：\n" + new Date() + "</p>";
        helper.sendMail(webSite.getMail(), webSite.getTitle() + "收到新评论", content);
    }

    /**
     * 递归查的顶级评论
     *
     * @param id 评论id
     * @return 评论id
     */
    private Integer findParent(Integer id) {
        Comment comment = detail(id);
        if (comment.getPid() == 0) {
            return comment.getId();
        }
        return findParent(comment.getPid());
    }

    /**
     * 根据id从数据库表中删除数据
     *
     * @param id 主键
     */
    public void delete(Integer id) {
        mapper.deleteById(id);
    }

    public PageHelper<Comment> list(Integer id, int pageNum) {
        Page<Comment> page = mapper.findAllByArticleAndPidOrderByCreatedDesc(Article.builder().id(id).build(), 0, PageRequest.of(pageNum - 1, 5));
        return PageHelper.<Comment>builder().rows(page.getContent().stream().peek(x -> x.setChildren(mapper.findAllByPidOrderByCreatedDesc(x.getId()))).collect(Collectors.toList())).current(pageNum).total(page.getTotalElements()).totalPage(page.getTotalPages()).build();
    }

    public void readAll() {
        mapper.readAll();
    }
}
