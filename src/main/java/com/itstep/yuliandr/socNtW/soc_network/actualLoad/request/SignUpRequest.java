package com.itstep.yuliandr.socNtW.soc_network.actualLoad.request;

import com.itstep.yuliandr.socNtW.soc_network.annotations.PasswordMatch;
import com.itstep.yuliandr.socNtW.soc_network.annotations.ValidEmail;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@PasswordMatch//custom annotation
public class SignUpRequest {

    @Email(message = "Error email format")
    @NotBlank(message = "Email is required field")
    @ValidEmail //custom annotation
    private String email;
    @NotEmpty(message = "Enter your name, please")
    private String firstname;
    @NotEmpty(message = "Enter your lastname, please")
    private String lastname;
    @NotEmpty(message = "Enter your login, please")
    private String username;
    @NotEmpty(message = "Password is required field")
    @Size(min=6)
    private String password;
    private String confirmPassword;
}
