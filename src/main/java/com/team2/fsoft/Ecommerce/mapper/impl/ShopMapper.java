package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.ShopDTO;
import com.team2.fsoft.Ecommerce.entity.Shop;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopMapper implements Mapper<Shop, ShopDTO> {

    @Override
    public Shop toEntity(ShopDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<ShopDTO, Shop> typeMap =  modelMapper.createTypeMap(ShopDTO.class, Shop.class);
        return modelMapper.map(dto, Shop.class);
    }

    @Override
    public ShopDTO toDTO(Shop entity) {
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<Shop, ShopDTO> typeMap =  modelMapper.createTypeMap(Shop.class, ShopDTO.class);
        return modelMapper.map(entity, ShopDTO.class);
    }

    @Override
    public List<ShopDTO> toDTOList(List<Shop> entityList) {
        return null;
    }

    @Override
    public List<Shop> toEntityList(List<ShopDTO> dtoList) {
        return null;
    }
}
