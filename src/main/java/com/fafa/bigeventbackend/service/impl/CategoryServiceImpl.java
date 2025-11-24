package com.fafa.bigeventbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fafa.bigeventbackend.model.entity.Category;
import com.fafa.bigeventbackend.model.request.AddCategoryRequest;
import com.fafa.bigeventbackend.model.request.UpdateCategoryRequest;
import com.fafa.bigeventbackend.service.CategoryService;
import com.fafa.bigeventbackend.mapper.CategoryMapper;
import com.fafa.bigeventbackend.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
* @author 86156
* @description 针对表【category】的数据库操作Service实现
* @createDate 2025-11-19 09:39:52
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void addCategory(AddCategoryRequest category) {
        // 获取创建分类的用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer)map.get("id");
        // 添加分类
        categoryMapper.addCategory(id, category);
    }

    @Override
    public List<Category> getCategoryList() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer)map.get("id");
        return categoryMapper.getCategoryList(userId);
    }

    @Override
    public Category getCategoryById(Integer id) {
        Category category = categoryMapper.getCategoryById(id);
        return category;
    }

    @Override
    public void updateCategory(UpdateCategoryRequest category) {
        categoryMapper.updateCategory(category);
    }
}




