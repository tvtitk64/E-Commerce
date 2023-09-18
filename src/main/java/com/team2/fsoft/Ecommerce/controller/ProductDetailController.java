package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.ProductDetailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product_details")
public class ProductDetailController {
    private final ProductDetailService productDetailService;

    public ProductDetailController(ProductDetailService productDetailService) {
        this.productDetailService = productDetailService;
    }

    @PutMapping("/updateInfo")
    public MessagesResponse updateProductDetail(@RequestBody ProductDetailReq productDetail, @RequestParam long id) {
        return productDetailService.updateInfo(productDetail, id);
    }

    @GetMapping("/{id}")
    public MessagesResponse updateProductDetail(@PathVariable long id ) {
        return productDetailService.findById(id);
    }
}
