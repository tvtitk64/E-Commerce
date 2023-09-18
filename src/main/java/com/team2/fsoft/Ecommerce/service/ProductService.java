package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.request.ProductRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;


import java.util.List;

public interface ProductService {
    MessagesResponse save(ProductReq productReq) ;


    MessagesResponse deleteById(long id);

    MessagesResponse getById(long id);

    PageDTO<ProductDetailResponse> getLists(ApiParameter apiParameter);
}
