package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailReq {
    public String color;

    public String size;

    private BigDecimal originPrice;

    private BigDecimal price;

    private int soldQuantity = 0;

    private int inStock = 0;
}
