package com.amigos.awsuploadimage.response;

import com.amigos.awsuploadimage.enumeration.AuthenticateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {
    private String jwt;
    private String username;
    private String email;
    private List<String> roles;
    private AuthenticateStatus status = AuthenticateStatus.SMS_OTP_REQUIRED;
    private String encodedSecret;

    public AuthenticateResponse(AuthenticateStatus status) {
        this.status = status;
    }
    public AuthenticateResponse(AuthenticateStatus status, String encodedSecret) {
        this.status = status;
        this.encodedSecret = encodedSecret;
    }
    public AuthenticateResponse(String jwt,String username,String email,List<String> roles,
                                AuthenticateStatus status) {
        this.jwt = jwt;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.status = status;
    }
}
