package com.team2.fsoft.Ecommerce.mapper.impl;

import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.entity.Role;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.enum_constant.Gender;
import com.team2.fsoft.Ecommerce.mapper.Mapper;
import com.team2.fsoft.Ecommerce.repository.RoleRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.List;

@Component
public class UserMapper implements Mapper<User, RegisterReq> {

    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User toEntity(RegisterReq dto) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(dto.getPassword());
        Gender gender = dto.getGender().equals("MALE") ? Gender.MALE : Gender.FEMALE;
        Role role = roleRepository.findByCode(dto.getRole()).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        TypeMap<RegisterReq, User> typeMap =  modelMapper.createTypeMap(RegisterReq.class,User.class);
        typeMap.addMappings(mapping->mapping.map(src->role,User::setRole));
        typeMap.addMappings(mapping->mapping.map(src->password,User::setPassword));
        typeMap.addMappings(mapping->mapping.map(src->gender,User::setGender));
        return modelMapper.map(dto,User.class);
    }

    @Override
    public RegisterReq toDTO(User entity) {
        return null;
    }

    @Override
    public List<RegisterReq> toDTOList(List<User> entityList) {
        return null;
    }

    @Override
    public List<User> toEntityList(List<RegisterReq> dtoList) {
        return null;
    }
}
