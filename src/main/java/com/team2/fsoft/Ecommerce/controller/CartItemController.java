package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.request.CartItemReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.CartItem;
import com.team2.fsoft.Ecommerce.service.CartItemService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart_items")
public class CartItemController {
    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping()
    public MessagesResponse addToCart(@RequestBody CartItemReq cartItemReq) {
        return cartItemService.add(cartItemReq);
    }
    @GetMapping("/GetMe")
    public  MessagesResponse getMe() {
        return cartItemService.getMe();
    }

    @DeleteMapping("/{id}")
    public MessagesResponse delete(@PathVariable @Positive Long id) {
        return  cartItemService.delete(id);
    }


}
