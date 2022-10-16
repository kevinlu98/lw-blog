package cn.kevinlu98.controller;

import cn.kevinlu98.common.ArticleSearch;
import cn.kevinlu98.common.WebSite;
import cn.kevinlu98.pojo.Article;
import cn.kevinlu98.pojo.Category;
import cn.kevinlu98.service.ArticleService;
import cn.kevinlu98.service.CategoryService;
import cn.kevinlu98.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.geom.Area;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * Author: Mr丶冷文
 * Date: 2022/10/6 16:20
 * Email: kevinlu98@qq.com
 * Description:
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final CommentService commentService;
    private final WebSite webSite;

    public AdminController(CategoryService categoryService, ArticleService articleService, CommentService commentService, WebSite webSite) {
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.commentService = commentService;
        this.webSite = webSite;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("category", categoryService.count());
        model.addAttribute("comment", commentService.count(null));
        model.addAttribute("commentFalse", commentService.count(false));
        model.addAttribute("article", articleService.count());
        model.addAttribute("newArticleList", articleService.search(ArticleSearch.builder().type(Article.TYPE_ARTICLE).pageNum(1).pageSize(5).build()).getRows());
        model.addAttribute("newCommentList", commentService.list(null, 1, 5).getRows());
        return "admin/index";
    }

    @GetMapping("/category.html")
    public String category() {
        return "admin/category";
    }

    @GetMapping("/friendly.html")
    public String friendly() {
        return "admin/friendly";
    }

    @GetMapping("/banner.html")
    public String banner() {
        return "admin/banner";
    }

    @GetMapping("/write.html")
    public String write(@RequestParam(required = false) Integer id, Model model) {
        List<Category> categories = categoryService.list();
        Article article = Objects.isNull(id) ? Article.builder().cover("").allowComment(1).build() : articleService.detail(id);
        model.addAttribute("categories", categories);
        model.addAttribute("article", article);
        model.addAttribute("pageTitle", Objects.isNull(article.getId()) ? "创建新文章" : "编辑 " + article.getTitle());
        return "admin/write";
    }

    @GetMapping("/article.html")
    public String article(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("categories", categories);
        return "admin/article";
    }

    @GetMapping("/comment.html")
    public String comment(@RequestParam(required = false) Boolean view, Model model) {
        model.addAttribute("isView", view);
        return "admin/comment";
    }

    @GetMapping("/navigation.html")
    public String navigation() {
        return "admin/navigation";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        String pass = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (StringUtils.equals(username, webSite.getUsername()) && StringUtils.equals(pass, webSite.getPassword())) {
            session.setAttribute(WebSite.LOGIN_SIGN, true);
            return "redirect:/admin/";
        } else {
            model.addAttribute("errorMsg", "用户名密码错误");
            return "admin/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(WebSite.LOGIN_SIGN);
        return "admin/login";
    }

    @GetMapping("/login.html")
    public String login() {
        return "admin/login";
    }
}
