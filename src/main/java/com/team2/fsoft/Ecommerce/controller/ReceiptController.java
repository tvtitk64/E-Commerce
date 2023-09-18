package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.response.CReceiptRes;
import com.team2.fsoft.Ecommerce.service.ReceiptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/getAllPurchase")
    List<CReceiptRes> GetAllPurchase() {
        return receiptService.getAllPurchase();
    }

    @GetMapping("/getAllBuy")
    @PreAuthorize("hasAuthority('SHOPPER') or hasAuthority('ADMIN')")
    List<CReceiptRes> GetAllBy() {
        return receiptService.getAllBuy();
    }
}
