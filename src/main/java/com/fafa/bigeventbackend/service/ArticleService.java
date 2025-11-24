package com.fafa.bigeventbackend.service;

import com.fafa.bigeventbackend.model.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fafa.bigeventbackend.model.entity.PageBean;
import com.fafa.bigeventbackend.model.request.AddArticleRequest;
import com.fafa.bigeventbackend.model.request.UpdateArticleRequest;

/**
* @author 86156
* @description 针对表【article】的数据库操作Service
* @createDate 2025-11-19 09:39:52
*/
public interface ArticleService extends IService<Article> {

    void addArticle(AddArticleRequest article);

    PageBean<Article> getArticleList(Integer pageNum, Integer pageSize, String categoryId, String state);

    void updateArticle(UpdateArticleRequest articleRequest);
}
