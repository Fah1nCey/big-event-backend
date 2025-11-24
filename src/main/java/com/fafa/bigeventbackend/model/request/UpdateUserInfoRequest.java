package com.fafa.bigeventbackend.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新基本信息请求体
 */
@Data
public class UpdateUserInfoRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -622174482496091438L;

    @NotNull // 值不能为null
    private Integer id;

    @NotEmpty // 值不能为null ，且内容不能为空
    @Pattern(regexp="^\\S{1,10}$")
    private String nickname;
    @NotEmpty
    @Email
    private String email;
}
