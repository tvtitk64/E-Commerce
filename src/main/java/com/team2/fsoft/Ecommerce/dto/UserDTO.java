package com.team2.fsoft.Ecommerce.dto;

import com.team2.fsoft.Ecommerce.enum_constant.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Email
    private String email;

    @Column(nullable = false)
    private  String name;

    private String address;

    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
