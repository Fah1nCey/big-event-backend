package com.fafa.bigeventbackend.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddCategoryRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 8615269217861249256L;

    //分类名称
    @NotEmpty(message = "分类名称不能为空")
    private String categoryName;
    //分类别名
    @NotEmpty(message = "分类别名不能为空")
    private String categoryAlias;
}
