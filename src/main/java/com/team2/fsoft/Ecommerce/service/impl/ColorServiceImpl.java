package com.team2.fsoft.Ecommerce.service.impl;


import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.entity.Color;
import com.team2.fsoft.Ecommerce.repository.CategoryRepository;
import com.team2.fsoft.Ecommerce.repository.ColorRepository;
import com.team2.fsoft.Ecommerce.service.CategoryService;
import com.team2.fsoft.Ecommerce.service.ColorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    final ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    public List<Color> getAll() {
        return colorRepository.findAll();
    }
}
