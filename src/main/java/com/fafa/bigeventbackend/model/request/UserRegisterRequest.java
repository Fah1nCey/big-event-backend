package com.fafa.bigeventbackend.model.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 115948410455715673L;

    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;
    @Pattern(regexp = "^\\S{5,16}$")
    private String rePassword;
}
