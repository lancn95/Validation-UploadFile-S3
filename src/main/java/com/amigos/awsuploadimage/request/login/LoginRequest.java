package com.amigos.awsuploadimage.request.login;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginId;
    private String password;
    private String token;
}
