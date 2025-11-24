package com.fafa.bigeventbackend.service;

import com.fafa.bigeventbackend.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fafa.bigeventbackend.model.request.AddCategoryRequest;
import com.fafa.bigeventbackend.model.request.UpdateCategoryRequest;

import java.util.List;

/**
* @author 86156
* @description 针对表【category】的数据库操作Service
* @createDate 2025-11-19 09:39:52
*/
public interface CategoryService extends IService<Category> {

    void addCategory(AddCategoryRequest category);

    List<Category> getCategoryList();

    Category getCategoryById(Integer id);

    void updateCategory(UpdateCategoryRequest category);
}
