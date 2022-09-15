package com.amigos.awsuploadimage.request.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignupRequestForm {
    @NotBlank(message = "username is 4 to 40 characters")
    @Size(min = 4, max = 40)
    private String username;

    @NotBlank(message = "email max is 40 characters")
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank(message = "password is 1 to 20 characters")
    @Size(min = 1, max = 20)
    private String password;

    private Set<String> role;
}
