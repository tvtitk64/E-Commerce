package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ProductDetailReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.ProductRes;
import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.mapper.impl.ProductDetailMapper;
import com.team2.fsoft.Ecommerce.repository.ProductDetailRepository;
import com.team2.fsoft.Ecommerce.service.ProductDetailService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    private final ProductDetailRepository productDetailRepository;

    private final ProductDetailMapper productDetailMapper;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository, ProductDetailMapper productDetailMapper) {
        this.productDetailRepository = productDetailRepository;
        this.productDetailMapper = productDetailMapper;
    }

    @Override
    public MessagesResponse findById(long id) {
        MessagesResponse ms = new MessagesResponse();
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(id);
        if (productDetailOptional.isPresent()) {
            ProductDetail productDetail = productDetailOptional.get();
            Product product = productDetail.getProduct();
            ms.data= new ProductRes(product.getId(),product.getName(),product.getDescription(),productDetail.getOriginPrice(),
                    productDetail.getPrice(),product.getCategory().getCode(),productDetail.getColor().getCode(),productDetail.getSize().getCode(),productDetail.getInStock(),productDetail.getSoldQuantity());
        } else  {
            ms.code=404;
            ms.message="Không tìm thấy thông tin sản phẩm, vui lòng thử lại!";
        }
        return  ms;
    }

    @Override
    public MessagesResponse updateInfo(ProductDetailReq productDetailRequest, long id) {
        MessagesResponse ms = new MessagesResponse();
        Optional<ProductDetail> productDetail1 = productDetailRepository.findById(id);
        if (productDetail1.isPresent()) {
            ms.data = productDetailMapper.toDTO(productDetail1.get());
        } else  {
            ms.code=404;
            ms.message="Không tìm thấy thông tin sản phẩm, vui lòng thử lại!";
        }
        return  ms;
    }
}
