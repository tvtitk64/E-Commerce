package com.team2.fsoft.Ecommerce.entity;

import com.team2.fsoft.Ecommerce.enum_constant.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(indexes = {
        @Index(name = "name_index",columnList = "name")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends  BaseEntity {

    @Email(message = "Email isn't valid")
    @Column(unique = true,nullable = false)
    private  String email;

//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$\n",message = "Password isn't valid")
    private String password;

    @Column(nullable = false)
    private  String name;

    private String address;


    @Min(5)
    @Max(100)
    private int age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_code")
    private Role role;

}
