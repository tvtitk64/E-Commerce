package com.team2.fsoft.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {
    public long id;
    public String name;
    public String address;
    public String phoneNumber;
    public String avatarUrl;

}
