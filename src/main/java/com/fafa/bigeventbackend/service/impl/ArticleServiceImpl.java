package com.fafa.bigeventbackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fafa.bigeventbackend.model.entity.Article;
import com.fafa.bigeventbackend.model.entity.PageBean;
import com.fafa.bigeventbackend.model.request.AddArticleRequest;
import com.fafa.bigeventbackend.model.request.UpdateArticleRequest;
import com.fafa.bigeventbackend.model.vo.ArticleListVO;
import com.fafa.bigeventbackend.service.ArticleService;
import com.fafa.bigeventbackend.mapper.ArticleMapper;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addArticle(AddArticleRequest addArticleRequest, String coverImgCos) {
        // 封装文章对象
        Article article = new Article();
        BeanUtils.copyProperties(addArticleRequest, article);
        // 获取创建人id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer createUser = (Integer) map.get("id");
        article.setCreateUser(createUser);
        article.setCoverImg(coverImgCos);
        articleMapper.addArticle(article);
    }

    @Override
    public PageBean<ArticleListVO> getArticleList(Integer pageNum, Integer pageSize, String categoryId, String state) {
        // 初始化分页返回结果对象
        PageBean<ArticleListVO> pageBean = new PageBean<>();
        // 初始化分页对象
        // 继承 BaseMapper<Article> 仅约束 MyBatis-Plus 内置的通用 CRUD 方法的泛型，而你自定义的 Mapper 方法中，
        // Page 对象的泛型可以是任意类型（VO/DTO/Entity 均可），和 BaseMapper 的泛型无关。
        Page<ArticleListVO> page = new Page<>(pageNum, pageSize);
        // 获取当前用户id
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        // 获取文章列表
        Page<ArticleListVO> articlePage = articleMapper.getArticleList(userId, page, categoryId, state);
        pageBean.setItems(articlePage.getRecords());
        pageBean.setTotal(articlePage.getTotal());
        return pageBean;
    }

    @Override
    public void updateArticle(UpdateArticleRequest articleRequest, String coverImgCos) {
        Article article = new Article();
        BeanUtils.copyProperties(articleRequest, article);
        article.setCoverImg(coverImgCos);
        article.setUpdateTime(new Date());
        this.updateById(article);
    }

}




