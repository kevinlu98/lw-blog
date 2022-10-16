package cn.kevinlu98.controller;

import cn.kevinlu98.common.PageHelper;
import cn.kevinlu98.common.Result;
import cn.kevinlu98.common.WebSite;
import cn.kevinlu98.enums.ResultEnum;
import cn.kevinlu98.pojo.Article;
import cn.kevinlu98.pojo.Comment;
import cn.kevinlu98.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 22:32
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@RestController
@RequestMapping("/admin/comment")
public class CommentController {

    private final CommentService service;

    private final WebSite webSite;

    public CommentController(CommentService service, WebSite webSite) {
        this.service = service;
        this.webSite = webSite;
    }

    @GetMapping("/")
    public Result<PageHelper<Comment>> list(@RequestParam(required = false) Boolean view, @RequestParam(required = false, defaultValue = "1") Integer pn, @RequestParam(required = false, defaultValue = "1") Integer ps) {
        pn = Math.max(1, pn);
        ps = Math.max(1, ps);
        return Result.success(service.list(view, pn, ps));
    }

    @GetMapping("/{id}")
    public Result<Comment> detail(@PathVariable Integer id) {
        Comment comment = service.detail(id);
        if (Objects.isNull(comment)) {
            return Result.error(ResultEnum.RESULT_DATA_NOT_FIND);
        } else {
            return Result.success(comment);
        }
    }


    @PostMapping("/")
    public Result<String> save(Comment comment) {
        service.save(comment);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return Result.success();
    }

    @PostMapping("/{pid}")
    public Result<String> replay(@PathVariable Integer pid, Integer aid, String message) {
        Comment comment = Comment.builder().article(Article.builder().id(aid).build()).email(webSite.getMail()).nickname(webSite.getNickname()).content(message).pid(pid).url(webSite.getUrl()).build();
        service.save(comment);
        return Result.success();
    }

    @GetMapping("/read")
    public Result<String> read() {
        service.readAll();
        return Result.success();
    }
}
