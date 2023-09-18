package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailReq;
import com.team2.fsoft.Ecommerce.entity.*;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import com.team2.fsoft.Ecommerce.repository.ColorRepository;
import com.team2.fsoft.Ecommerce.repository.SizeRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDetailMapper implements Mapper<ProductDetail, ProductDetailReq> {
    final  ColorRepository colorRepository;
    final  SizeRepository sizeRepository;

    public ProductDetailMapper(ColorRepository colorRepository, SizeRepository sizeRepository) {
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public ProductDetail toEntity(ProductDetailReq dto) {
        ModelMapper modelMapper = new ModelMapper();
        Color color = colorRepository.findByCode(dto.getColor()).get();
        Size size = sizeRepository.findByCode(dto.getSize()).get();
        TypeMap<ProductDetailReq, ProductDetail> typeMap =  modelMapper.createTypeMap(ProductDetailReq.class, ProductDetail.class);
        typeMap.addMappings(mapping->mapping.map(src->color, ProductDetail::setColor));
        typeMap.addMappings(mapping->mapping.map(src->size, ProductDetail::setSize));
        return modelMapper.map(dto, ProductDetail.class);

    }

    @Override
    public ProductDetailReq toDTO(ProductDetail entity) {
        return null;
    }

    @Override
    public List<ProductDetailReq> toDTOList(List<ProductDetail> entityList) {
        return null;
    }

    @Override
    public List<ProductDetail> toEntityList(List<ProductDetailReq> dtoList) {
        return null;
    }
}
