package com.itstep.yuliandr.socNtW.soc_network.payload.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "User name can't be empty")
    private String username;
    @NotEmpty(message = "Password can't be empty")
    private String password;
}
