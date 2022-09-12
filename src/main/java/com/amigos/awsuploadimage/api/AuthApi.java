package com.amigos.awsuploadimage.api;

import com.amigos.awsuploadimage.enumeration.AuthenticateStatus;
import com.amigos.awsuploadimage.request.login.LoginRequest;
import com.amigos.awsuploadimage.response.AuthenticateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthApi {

    @PostMapping(value = "/authenticate")
    public AuthenticateResponse authenticate(@RequestBody LoginRequest request, HttpServletRequest httpRequest){
        AuthenticateResponse response = new AuthenticateResponse(AuthenticateStatus.AUTHENTICATED);
        return response;
    }
}
