package com.fafa.bigeventbackend.controller;

import com.fafa.bigeventbackend.common.Result;
import com.fafa.bigeventbackend.model.entity.Category;
import com.fafa.bigeventbackend.model.request.AddCategoryRequest;
import com.fafa.bigeventbackend.model.request.UpdateCategoryRequest;
import com.fafa.bigeventbackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // 添加文章分类
    @PostMapping
    public Result addCategory(@RequestBody @Validated AddCategoryRequest category) {
        categoryService.addCategory(category);
        return Result.success();
    }

    // 获取文章分类列表
    @GetMapping
    public Result<List<Category>> getCategoryList() {
        // TODO: 2025/11/21 需要返回创建人给前端吗？ 
        List<Category> categoryList = categoryService.getCategoryList();
        return Result.success(categoryList);
    }

    // 获取文章分类详情
    @GetMapping("/detail")
    public Result<Category> getCategoryDetail(@RequestParam Integer id) {
        if (id == null) {
            return Result.error("分类id不能为空");
        }
        Category category = categoryService.getCategoryById(id);
        return Result.success(category);
    }

    // 更新文章分类
    @PutMapping
    public Result updateCategory(@RequestBody @Validated UpdateCategoryRequest category) {
        categoryService.updateCategory(category);
        return Result.success();
    }

    // 删除文章分类
    @DeleteMapping
    public Result deleteCategory(@RequestParam Integer id) {
        if (id == null) {
            return Result.error("分类id不能为空");
        }
        // 直接使用mybatis-plus的removeById方法删除
        categoryService.removeById(id);
        return Result.success();
    }

}
