package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.response.CReceiptRes;
import com.team2.fsoft.Ecommerce.dto.response.ReceiptRes;
import com.team2.fsoft.Ecommerce.repository.ReceiptRepository;
import com.team2.fsoft.Ecommerce.service.ReceiptService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    private final ReceiptRepository receiptRepository;

    public ReceiptServiceImpl(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    @Override
    public List<CReceiptRes> getAllPurchase() {
        Authentication authentication =   SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<List<ReceiptRes>> receiptResList = receiptRepository.getAllPurchase(email);
       if (receiptResList.isPresent()) {
           List<ReceiptRes> receiptResListResult = receiptResList.get();
           List<CReceiptRes> cReceiptRes = receiptResListResult.stream().map(receiptRes->
                   new CReceiptRes(receiptRes.getProductName(),receiptRes.getShopName(),receiptRes.getPrice(),receiptRes.getAmount(),receiptRes.getPayment(),
                           receiptRes.getVat(),receiptRes.getReceiveTime())).collect(Collectors.toList());
           return  cReceiptRes;
       }
       return null;
    }

    @Override
    public List<CReceiptRes> getAllBuy() {
        Authentication authentication =   SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<List<ReceiptRes>> receiptResList = receiptRepository.getAllBuy(email);
        if (receiptResList.isPresent()) {
            List<ReceiptRes> receiptResListResult = receiptResList.get();
            List<CReceiptRes> cReceiptRes = receiptResListResult.stream().map(receiptRes->
                    new CReceiptRes(receiptRes.getProductName(),receiptRes.getShopName(),receiptRes.getPrice(),receiptRes.getAmount(),receiptRes.getPayment(),
                            receiptRes.getVat(),receiptRes.getReceiveTime())).collect(Collectors.toList());
            return  cReceiptRes;
        }
        return null;
    }


}
