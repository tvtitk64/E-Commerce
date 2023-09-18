package com.team2.fsoft.Ecommerce.service.impl;


import com.team2.fsoft.Ecommerce.entity.Color;
import com.team2.fsoft.Ecommerce.entity.Size;
import com.team2.fsoft.Ecommerce.repository.ColorRepository;
import com.team2.fsoft.Ecommerce.repository.SizeRepository;
import com.team2.fsoft.Ecommerce.service.ColorService;
import com.team2.fsoft.Ecommerce.service.SizeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }
}
