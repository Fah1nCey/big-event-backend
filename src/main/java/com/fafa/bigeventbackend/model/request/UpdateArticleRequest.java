package com.fafa.bigeventbackend.model.request;

import com.fafa.bigeventbackend.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UpdateArticleRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1347408401521831148L;

    //主键ID
    @NotNull(message = "id不能为空")
    private Integer id;
    //文章标题
    @NotEmpty(message = "标题不能为空")
    private String title;
    //文章内容
    @NotEmpty(message = "内容不能为空")
    private String content;
    //封面图像
    @NotEmpty(message = "封面图片不能为空")
    private String coverImg;
    //发布状态 已发布|草稿
    @State
    private String state;
    //文章分类id
    @NotNull(message = "分类id不能为空")
    private Integer categoryId;
}
