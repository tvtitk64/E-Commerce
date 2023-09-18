package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.request.CartItemReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.CartItem;

public interface CartItemService {
    MessagesResponse add(CartItemReq cartItemReq);

    MessagesResponse getMe();

    MessagesResponse delete(Long id);
}
