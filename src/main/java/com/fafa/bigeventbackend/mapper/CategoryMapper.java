package com.fafa.bigeventbackend.mapper;

import com.fafa.bigeventbackend.model.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fafa.bigeventbackend.model.request.AddCategoryRequest;
import com.fafa.bigeventbackend.model.request.UpdateCategoryRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 86156
* @description 针对表【category】的数据库操作Mapper
* @createDate 2025-11-19 09:39:52
* @Entity com.fafa.bigeventbackend.model.entity.Category
*/
public interface CategoryMapper extends BaseMapper<Category> {

    @Insert("insert into category (category_name, category_alias, create_user, create_time, update_time) " +
            "values (#{category.categoryName}, #{category.categoryAlias}, #{id}, now(), now())")
    void addCategory(@Param("id") Integer id, @Param("category") AddCategoryRequest category);

    @Select("select * from category where create_user = #{userId}")
    List<Category> getCategoryList(Integer userId);

    @Select("select * from category where id = #{id}")
    Category getCategoryById(Integer id);

    @Update("update category set category_name = #{categoryName}, category_alias = #{categoryAlias}, " +
            "update_time = now() where id = #{id}")
    void updateCategory(UpdateCategoryRequest category);
}




