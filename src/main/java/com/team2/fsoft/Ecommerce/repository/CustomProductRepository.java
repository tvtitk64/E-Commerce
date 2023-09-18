package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.entity.Product;

import java.util.List;

public interface CustomProductRepository{
    List<Product> getByFilter(ApiParameter apiParameter);
}