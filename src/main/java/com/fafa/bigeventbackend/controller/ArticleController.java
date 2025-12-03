package com.fafa.bigeventbackend.controller;


import com.fafa.bigeventbackend.common.Result;
import com.fafa.bigeventbackend.manager.CosManager;
import com.fafa.bigeventbackend.model.entity.Article;
import com.fafa.bigeventbackend.model.entity.PageBean;
import com.fafa.bigeventbackend.model.request.AddArticleRequest;
import com.fafa.bigeventbackend.model.request.UpdateArticleRequest;
import com.fafa.bigeventbackend.model.vo.ArticleListVO;
import com.fafa.bigeventbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FileUploadController fileUploadController;

    @Autowired
    private CosManager cosManager;

    /**
     * 发布文章(文件 + 普通参数)
     * @param addArticleRequest
     * @param coverImg
     * @return
     */
    @PostMapping
    public Result addArticle(
            @Validated @ModelAttribute AddArticleRequest addArticleRequest,
            @RequestPart("coverImg") MultipartFile coverImg) {
        String coverImgCos = fileUploadController.upload(coverImg);
        articleService.addArticle(addArticleRequest, coverImgCos);
        return Result.success();
    }

    /**
     * 获取文章列表(分页)
     * @return
     */
    @GetMapping
    public Result<PageBean<ArticleListVO>> getArticleList(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) String state) {
        PageBean<ArticleListVO> articleList = articleService.getArticleList(pageNum, pageSize, categoryId, state);
        return Result.success(articleList);
    }

    // 获取文章详情
    @GetMapping("/detail")
    public Result<Article> getArticleDetail(Integer id) {
        if (id == null) {
            return Result.error("文章ID不能为空");
        }
        Article article = articleService.getById(id);
        if(article == null) {
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    // 更新文章
    @PutMapping
    public Result updateArticle(
            @Validated @ModelAttribute UpdateArticleRequest articleRequest,
            @RequestPart("coverImg") MultipartFile coverImg) {
        // 更新文章不需要将createUser作为查询条件，因为只有创建者能看到自己的文章（查找文章时指定了）
        String coverImgCos = fileUploadController.upload(coverImg);
        try {
            articleService.updateArticle(articleRequest, coverImgCos);
        } catch (Exception e) {
            cosManager.deleteObject(coverImgCos);
            throw e;
        }
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
