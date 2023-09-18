package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ProductReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductDetailResponse;
import com.team2.fsoft.Ecommerce.service.ProductService;
import jakarta.validation.constraints.Positive;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SHOPPER')")
    public MessagesResponse create(@RequestBody ProductReq productReq) {
        return  productService.save(productReq);
    }

    @GetMapping("/{id}")
    public  MessagesResponse GetById(@PathVariable @Positive long id) {
        return  productService.getById(id);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SHOPPER') or hasAuthority('ADMIN')")
    public  MessagesResponse Delete(@PathVariable @Positive long id) {
        return  productService.deleteById(id);
    }

    @PostMapping("/api/search")
    public PageDTO<ProductDetailResponse> GetLists(@RequestBody ApiParameter apiParameter){
        return  productService.getLists(apiParameter);
    }
}
