package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.ColorService;
import com.team2.fsoft.Ecommerce.service.SizeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sizes")
public class SizeController {
    private  final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public MessagesResponse getAll() {
        MessagesResponse ms = new MessagesResponse();
        try {
            ms.data = sizeService.getAll();

        }
        catch (Exception e) {
            ms.code = 500;
            ms.setMessage("Intenal Server Error!");
        }
        return  ms;
    }
}
