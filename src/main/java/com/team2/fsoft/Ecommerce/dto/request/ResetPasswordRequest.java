package com.team2.fsoft.Ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @Email
    private String email;

    @NotNull
    private int otpNum;

    private String newPassword;
}
