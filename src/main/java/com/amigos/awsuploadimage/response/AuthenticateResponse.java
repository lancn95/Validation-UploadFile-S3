package com.amigos.awsuploadimage.response;

import com.amigos.awsuploadimage.enumeration.AuthenticateStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class AuthenticateResponse {
    private AuthenticateStatus status = AuthenticateStatus.SMS_OTP_REQUIRED;
    private String encodedSecret;

    public AuthenticateResponse(AuthenticateStatus status) {
        this.status = status;
    }
    public AuthenticateResponse(AuthenticateStatus status, String encodedSecret) {
        this.status = status;
        this.encodedSecret = encodedSecret;
    }
}
