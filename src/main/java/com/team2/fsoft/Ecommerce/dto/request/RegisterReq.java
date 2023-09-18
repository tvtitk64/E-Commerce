package com.team2.fsoft.Ecommerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReq {
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String password;

//    private String phoneNumber;

    private String address;
//    private Date birthDay;
    private String role;

    private int age;

    private String gender;

}
