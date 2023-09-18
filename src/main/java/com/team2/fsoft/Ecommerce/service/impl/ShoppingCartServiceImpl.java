package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.entity.ShoppingCart;
import com.team2.fsoft.Ecommerce.repository.ShoppingCartRepository;
import com.team2.fsoft.Ecommerce.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public int getCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        ShoppingCart shoppingcart = shoppingCartRepository.findByUserEmail(email).get();
        return shoppingcart.getCount();
    }
}
