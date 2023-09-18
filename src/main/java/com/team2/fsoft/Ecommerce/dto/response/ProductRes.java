package com.team2.fsoft.Ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRes {
    public  long id;

    public String name;

    public String description;

    public int originPrice;

    public int price;

    public String category;

    public String color;

    public  String size;

    public int inStock;

    public int soldQuantity;
}
