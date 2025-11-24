package com.fafa.bigeventbackend.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UpdateUserPwdRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1468931514191377284L;

    // 映射前端的 old_pwd，提示“旧密码不能为空”
    @JsonProperty("old_pwd")
    @NotEmpty(message = "旧密码不能为空")
    private String oldPwd;

    // 映射前端的 new_pwd，提示“新密码不能为空”
    @JsonProperty("new_pwd")
    @NotEmpty(message = "新密码不能为空")
    private String newPwd;

    // 映射前端的 re_pwd，提示“确认密码不能为空”
    @JsonProperty("re_pwd")
    @NotEmpty(message = "确认密码不能为空")
    private String rePwd;
}
