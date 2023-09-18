package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.entity.Role;
import com.team2.fsoft.Ecommerce.repository.RoleRepository;
import com.team2.fsoft.Ecommerce.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
