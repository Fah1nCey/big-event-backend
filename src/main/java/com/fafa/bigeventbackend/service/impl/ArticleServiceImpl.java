package com.fafa.bigeventbackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fafa.bigeventbackend.manager.CosManager;
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

    @Autowired
    private CosManager cosManager;

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
        // 获取旧文章对象
        Article oldArticle = this.getById(articleRequest.getId());
        // 创建一个新的 Article 对象，并将更新请求中的属性复制到新对象中
        Article article = new Article();
        BeanUtils.copyProperties(articleRequest, article);
        article.setUpdateTime(new Date());
        // 保持旧图（默认）
        article.setCoverImg(oldArticle.getCoverImg());

        // 如果有新图片，先替换字段，但不删除旧图
        if (coverImgCos != null) {
            // 有新图
            article.setCoverImg(coverImgCos);
        }
        this.updateById(article);
        // 数据库更新成功之后，删除旧图片（如果有新图片）
        if (coverImgCos != null) {
            cosManager.deleteObject(oldArticle.getCoverImg());
        }
    }

}




