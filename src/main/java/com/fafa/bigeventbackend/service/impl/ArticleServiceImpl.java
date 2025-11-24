package com.fafa.bigeventbackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fafa.bigeventbackend.model.entity.Article;
import com.fafa.bigeventbackend.model.entity.PageBean;
import com.fafa.bigeventbackend.model.request.AddArticleRequest;
import com.fafa.bigeventbackend.model.request.UpdateArticleRequest;
import com.fafa.bigeventbackend.service.ArticleService;
import com.fafa.bigeventbackend.mapper.ArticleMapper;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;

/**
* @author 86156
* @description 针对表【article】的数据库操作Service实现
* @createDate 2025-11-19 09:39:52
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void addArticle(AddArticleRequest article) {
        // 获取创建人id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer createId = (Integer) map.get("id");
        articleMapper.addArticle(article, createId);
    }

    @Override
    public PageBean<Article> getArticleList(Integer pageNum, Integer pageSize, String categoryId, String state) {
        // 初始化分页返回结果对象
        PageBean<Article> pageBean = new PageBean<>();
        // 初始化分页对象
        Page<Article> page = new Page<>(pageNum, pageSize);
        // 获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        // 获取文章列表
        Page<Article> articlePage = articleMapper.getArticleList(userId, page, categoryId, state);
        pageBean.setItems(articlePage.getRecords());
        pageBean.setTotal(articlePage.getTotal());
        return pageBean;
    }

    @Override
    public void updateArticle(UpdateArticleRequest articleRequest) {
        Article article = new Article();
        BeanUtils.copyProperties(articleRequest, article);
        article.setUpdateTime(new Date());
        this.updateById(article);
    }

}




