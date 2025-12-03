package com.fafa.bigeventbackend.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fafa.bigeventbackend.model.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fafa.bigeventbackend.model.request.AddArticleRequest;
import com.fafa.bigeventbackend.model.vo.ArticleListVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
* @author 86156
* @description 针对表【article】的数据库操作Mapper
* @createDate 2025-11-19 09:39:52
* @Entity com.fafa.bigeventbackend.model.entity.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    @Insert("insert into article (title, content, cover_img, state, category_id, create_user, create_time, update_time) " +
            "values (#{title}, #{content}, #{coverImg}," +
            " #{state}, #{categoryId}, #{createUser}, now(), now())")
    void addArticle(Article article);

    Page<ArticleListVO> getArticleList(@Param("userId") Integer userId, Page<ArticleListVO> page,
                                       @Param("categoryId") String categoryId,
                                       @Param("state") String state);

}




