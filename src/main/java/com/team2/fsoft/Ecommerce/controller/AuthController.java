package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.constant.ExceptionMessage;
import com.team2.fsoft.Ecommerce.dto.request.LoginRequest;
import com.team2.fsoft.Ecommerce.dto.request.RefreshTokenRq;
import com.team2.fsoft.Ecommerce.dto.response.LoginResponse;
import com.team2.fsoft.Ecommerce.exception.InvalidRefreshToken;
import com.team2.fsoft.Ecommerce.security.JWTService;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.security.UserDetailService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserDetailService userDetailService;

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JWTService jwtService, UserDetailService userDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService=jwtService;
        this.userDetailService=userDetailService;
    }

    @PostMapping("/login")
    public LoginResponse authenticateAccount(@Valid @RequestBody LoginRequest loginRequest) {

        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();


        String accessToken = jwtService.generateToken(userDetail);
        String refreshToken = jwtService.generateRefreshToken(userDetail);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserName(userDetail.getUser().getName());
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);

        return loginResponse;

    }
    @PostMapping("/refresh")
    public LoginResponse authenticateAccount(@Valid @RequestBody RefreshTokenRq refreshTokenRq) {

         String token = refreshTokenRq.getRefreshToken();
         if (!jwtService.isValidToken(token)) {
             throw new InvalidRefreshToken(ExceptionMessage.REFRESH_TOKEN_INVALID);
         }
            String email = jwtService.getEmailFromRefreshToken(token);
            UserDetail userDetail = (UserDetail)userDetailService.loadUserByUsername(email);
            String accessToken = jwtService.generateToken(userDetail);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserName(userDetail.getUser().getName());
            loginResponse.setAccessToken(accessToken);
            loginResponse.setRefreshToken(token);
            return loginResponse;

    }
}
