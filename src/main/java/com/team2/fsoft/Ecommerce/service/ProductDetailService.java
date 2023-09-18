package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;

public interface ProductDetailService {
    MessagesResponse findById(long id);

    MessagesResponse updateInfo(ProductDetailReq productDetailRequest, long id);
}
