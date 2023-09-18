package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.CartItemReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.CartItem;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import com.team2.fsoft.Ecommerce.entity.ShoppingCart;
import com.team2.fsoft.Ecommerce.repository.CartItemRepository;
import com.team2.fsoft.Ecommerce.repository.ProductDetailRepository;
import com.team2.fsoft.Ecommerce.repository.ProductRepository;
import com.team2.fsoft.Ecommerce.repository.ShoppingCartRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

    final CartItemRepository cartItemRepository;
    final ShoppingCartRepository shoppingCartRepository;
    final ProductRepository productRepositoty;

    final
    ProductDetailRepository productDetailRepository;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, ShoppingCartRepository shoppingCartRepository, ProductRepository productRepositoty, ProductDetailRepository productDetailRepository) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepositoty = productRepositoty;
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    @Transactional
    public MessagesResponse add(CartItemReq cartItemReq) {
        MessagesResponse ms = new MessagesResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email).get();
        try {
            Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(cartItemReq.product_detail_id);
            if (productDetailOptional.isPresent() && productDetailOptional.get().getInStock() - cartItemReq.amount > 0) {
                ProductDetail productDetail = productDetailOptional.get();
                CartItem cartItem1 = new CartItem();
                cartItem1.setShoppingCart(shoppingCart);
                cartItem1.setProductDetail(productDetail);
                cartItem1.setAmount(cartItemReq.amount);
                cartItemRepository.save(cartItem1);
                shoppingCart.setCount(shoppingCart.getCount() + 1);
                shoppingCartRepository.save(shoppingCart);
                productDetail.setInStock((productDetail.getInStock()) - cartItemReq.amount);
                productDetail.setSoldQuantity(productDetail.getSoldQuantity() + cartItemReq.amount);
                productDetailRepository.save(productDetail);
            } else {
                ms.code = 500;
                ms.message = " Không đủ hàng";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = "Không thể thêm mới item này vào giỏ hàng";
        }

        ms.data = shoppingCart.getCount();
        return ms;
    }

    @Override
    public MessagesResponse getMe() {
        MessagesResponse ms = new MessagesResponse();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(email).get();
            List<CartItem> list = cartItemRepository.findShoppingCartId(shoppingCart.getId()).get();
            ms.data = list;
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Internal Server Error";
        }
        return ms;
    }

    @Override
    public MessagesResponse delete(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            CartItem cartItem = cartItemRepository.findById(id).get();
            ShoppingCart shoppingCart = cartItem.getShoppingCart();
            cartItemRepository.delete(cartItem);
            shoppingCart.setCount(shoppingCart.getCount() - 1);
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Internal Server Error";
        }
        return ms;
    }
}
