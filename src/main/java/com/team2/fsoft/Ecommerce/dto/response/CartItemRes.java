package com.team2.fsoft.Ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRes {
    public long id;
    public  long productDetailId;
    public String productName;
    public  String productBrand;
    public  int currentPrice;
    public int quantity;
    public String buyType;
    public long campaignId;
}
