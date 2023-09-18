package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.ShopDTO;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;

public interface ShopService {
    MessagesResponse save(ShopDTO shopReq);

    MessagesResponse getInfo();
}
