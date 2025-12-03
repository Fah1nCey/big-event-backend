package com.fafa.bigeventbackend.model.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2550226009784551611L;
    @Pattern(regexp = "^\\S{5,16}$")
    private String username;
    @Pattern(regexp = "^\\S{5,16}$")
    private String password;
}
