package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductReq {
    public int id;
    public String name;
    public String description;
    public String category;

    public List<ProductDetailReq> productDetailReqList;



}
