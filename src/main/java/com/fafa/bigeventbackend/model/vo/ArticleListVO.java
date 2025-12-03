package com.fafa.bigeventbackend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * VO=View Object，视图对象，专门面向前端响应
 */
@Data
public class ArticleListVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7013723196783603610L;

    //主键ID
    private Integer id;
    //文章标题
    private String title;
    //发布状态 已发布|草稿
    private String state;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //分类名称
    private String categoryName;
}
