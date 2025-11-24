package com.fafa.bigeventbackend.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UpdateCategoryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 6846417448353442636L;

    //分类ID
    @NotNull(message = "分类ID不能为null")
    private Integer id;
    //分类名称
    @NotEmpty(message = "分类名称不能为空")
    private String categoryName;
    //分类别名
    @NotEmpty(message = "分类别名不能为空")
    private String categoryAlias;
}
