package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.entity.Role;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import com.team2.fsoft.Ecommerce.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserResMapper implements Mapper<User, UserRes> {
    private final RoleRepository roleRepository;

    public UserResMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public User toEntity(UserRes dto) {

        Role role = roleRepository.findByCode(dto.getRole()).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<UserRes,User> typeMap =  modelMapper.createTypeMap(UserRes.class,User.class);
        typeMap.addMappings(mapping->mapping.map(src->role,User::setRole));

        return modelMapper.map(dto,User.class);

    }

    @Override
    public UserRes toDTO(User entity) {
        String role = entity.getRole().getCode();

        ModelMapper modelMapper = new ModelMapper();
        TypeMap<User,UserRes> typeMap =  modelMapper.createTypeMap(User.class,UserRes.class);
        typeMap.addMappings(mapping->mapping.map(src->role,UserRes::setRole));
        return modelMapper.map(entity,UserRes.class);
    }

    @Override
    public List<UserRes> toDTOList(List<User> entityList) {
        return entityList.stream().map(entity->toDTO(entity)).collect(Collectors.toList());
    }

    @Override
    public List<User> toEntityList(List<UserRes> dtoList) {
        return null;
    }
}
