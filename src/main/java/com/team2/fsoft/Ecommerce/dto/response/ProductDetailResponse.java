package com.team2.fsoft.Ecommerce.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailResponse {
    public int id;
    public String name;
    public String description;
    public String category;
    private String color;
    private String size;
    private BigDecimal originPrice;
    private BigDecimal price;
}
