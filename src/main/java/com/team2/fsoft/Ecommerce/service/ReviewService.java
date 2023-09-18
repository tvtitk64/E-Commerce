package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.request.ReviewReq;
import com.team2.fsoft.Ecommerce.dto.response.ReviewRes;

import java.util.List;

public interface ReviewService {
    void add(ReviewReq reviewReq);


    List<ReviewRes> findAllByProductId(long productId);
}
