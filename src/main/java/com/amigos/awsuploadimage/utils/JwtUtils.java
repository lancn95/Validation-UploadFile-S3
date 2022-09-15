package com.amigos.awsuploadimage.utils;

import com.amigos.awsuploadimage.service.impl.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${lan.app.jwtSecret}")
    private String jwtSecret;

    @Value("${lan.app.jwtExpirationMs}")
    private Long jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date().getTime()) + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        }catch (SignatureException e){
            Logger.error("invalid JWT signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            Logger.error("Invalid JWT token: {}", e.getMessage());
        }catch (ExpiredJwtException e){
            Logger.error("JWT token is exprired: {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            Logger.error("JWT token is unsupportedJwtException: {}", e.getMessage());
        }
        catch (IllegalArgumentException e){
            Logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
