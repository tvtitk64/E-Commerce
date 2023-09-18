package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.entity.ShipFee;
import com.team2.fsoft.Ecommerce.repository.CategoryRepository;
import com.team2.fsoft.Ecommerce.repository.ShipFeeRepository;
import com.team2.fsoft.Ecommerce.service.ShipFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShipFeeServiceImpl implements ShipFeeService {
    @Autowired
    ShipFeeRepository shipFeeRepository;
    @Override
    public List<ShipFee> getAll() {
        return shipFeeRepository.findAll();
    }
}
