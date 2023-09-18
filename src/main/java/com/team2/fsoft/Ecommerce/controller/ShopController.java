package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.ShopDTO;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.ShopService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController

public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }



    @PostMapping("/createShop")
    MessagesResponse createShop(@RequestBody ShopDTO shopDTO){
        return shopService.save(shopDTO);
    }

    @GetMapping("/getShopInfo")
    MessagesResponse GetInfo(){
        return  shopService.getInfo();
    }

}