package com.fafa.bigeventbackend.controller;


import com.fafa.bigeventbackend.common.Result;
import com.fafa.bigeventbackend.model.entity.Article;
import com.fafa.bigeventbackend.model.entity.PageBean;
import com.fafa.bigeventbackend.model.request.AddArticleRequest;
import com.fafa.bigeventbackend.model.request.UpdateArticleRequest;
import com.fafa.bigeventbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result addArticle(@RequestBody @Validated AddArticleRequest article) {
        articleService.addArticle(article);
        return Result.success();
    }

    /**
     * 获取文章列表(分页)
     * @return
     */
    @GetMapping
    public Result<PageBean<Article>> getArticleList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String state) {
        PageBean<Article> articleList = articleService.getArticleList(pageNum, pageSize, categoryId, state);
        return Result.success(articleList);
    }

    // 获取文章详情
    @GetMapping("/detail")
    public Result<Article> getArticleDetail(Integer id) {
        if (id == null) {
            return Result.error("文章ID不能为空");
        }
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    // 更新文章
    @PutMapping
    public Result updateArticle(@RequestBody UpdateArticleRequest articleRequest) {
        // 更新文章不需要将createUser作为查询条件，因为只有创建者能看到自己的文章
        articleService.updateArticle(articleRequest);
        return Result.success();
    }

    // 删除文章
    @DeleteMapping
    public Result deleteArticle(Integer id) {
        if (id == null) {
            return Result.error("文章ID不能为空");
        }
        articleService.removeById(id);
        return Result.success();
    }
}
