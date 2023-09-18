package com.team2.fsoft.Ecommerce.service.impl;


import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.repository.CategoryRepository;
import com.team2.fsoft.Ecommerce.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    final  CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}
