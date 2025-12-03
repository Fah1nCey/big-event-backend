package com.fafa.bigeventbackend.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UpdateAvatarRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -2089144760030077742L;

    @NotEmpty(message = "头像不能为空")
    private String avatar;
}
