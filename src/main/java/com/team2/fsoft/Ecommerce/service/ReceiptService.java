package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.response.CReceiptRes;
import com.team2.fsoft.Ecommerce.dto.response.ReceiptRes;

import java.util.List;

public interface ReceiptService {
    List<CReceiptRes> getAllPurchase();

    List<CReceiptRes> getAllBuy();
}
