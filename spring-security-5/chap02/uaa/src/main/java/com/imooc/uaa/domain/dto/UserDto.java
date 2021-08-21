package com.imooc.uaa.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

// @PasswordMatches
@Data
public class UserDto implements Serializable {
    @NotNull
    @NotBlank
    @Size(min = 4, max = 50, message = "{demo.name}")
    private String username;
    @NotNull
    // @ValidPassword
    private String password;
    @NotNull
    private String matchingPassword;
    @NotNull
    // @ValidEmail
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 4, max = 50, message = "{demo.name.param}")
    private String name;
}
