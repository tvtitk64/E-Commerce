package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDetailResponseMapper implements Mapper<ProductDetail, ProductDetailResponse> {

    @Override
    public ProductDetail toEntity(ProductDetailResponse dto) {
        return null;
    }

    @Override
    public ProductDetailResponse toDTO(ProductDetail entity) {
        return null;
    }

    @Override
    public List<ProductDetailResponse> toDTOList(List<ProductDetail> entityList) {
        return entityList.stream().map(entity -> toDTO(entity)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDetail> toEntityList(List<ProductDetailResponse> dtoList) {
        return null;
    }
}
