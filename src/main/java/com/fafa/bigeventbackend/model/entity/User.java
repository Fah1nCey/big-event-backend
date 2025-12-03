package com.fafa.bigeventbackend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    //主键ID
    private Integer id;
    //用户名
    private String username;
    //密码
    @JsonIgnore //让springmvc把当前对象转换成json字符串时，忽略password字段
    private String password;
    //昵称
    private String nickname;
    //邮箱
    private String email;
    //用户头像地址
    private String userPic;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}