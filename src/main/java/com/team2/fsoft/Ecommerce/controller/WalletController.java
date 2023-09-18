package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/toup")
    public MessagesResponse ToUp(@RequestBody Integer money) {
        return walletService.ToUp(money);
    }

    @PostMapping("/withdraw")
    public MessagesResponse Withdraw(@RequestBody Integer money) {
        return walletService.Withdraw(money);
    }

    @GetMapping("/balance")
    public int GetBalance() {
        return walletService.getBalance();
    }
}
