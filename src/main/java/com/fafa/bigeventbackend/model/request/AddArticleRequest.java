package com.fafa.bigeventbackend.model.request;

import com.fafa.bigeventbackend.anno.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serial;
import java.io.Serializable;

@Data
public class AddArticleRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -8942656462031684604L;

    //文章标题
    @NotEmpty(message = "文章标题不能为空")
    @Pattern(regexp="^\\S{1,10}$")
    private String title;
    //文章内容
    @NotEmpty(message = "文章内容不能为空")
    private String content;
    //发布状态 已发布|草稿
    @NotEmpty(message = "发布状态不能为空")
    @State
    private String state;
    //文章分类id
    @NotNull(message = "文章分类不能为空")
    private Integer categoryId;
}
