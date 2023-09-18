package com.team2.fsoft.Ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String userName;
    private String accessToken;

    private String refreshToken;


    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
