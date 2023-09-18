package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.dto.response.ReceiptRes;
import com.team2.fsoft.Ecommerce.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    @Query(value = "select  ct.productDetail.product.name as productName ,ct.productDetail.product.shop.name as shopName," +
            "ct.productDetail.price as price , ct.amount as amount , rc.payment as payment, rc.vat as vat, " +
            "rc.order.receiveTime  as receiveTime" +
            " from Receipt  rc join rc.order.cartItems ct where rc.order.author=:email")
    Optional<List<ReceiptRes>> getAllPurchase(@Param("email") String email);

    @Query(value = "select  ct.productDetail.product.name as productName ,ct.productDetail.product.shop.name as shopName," +
            "ct.productDetail.price as price , ct.amount as amount , rc.payment as payment, rc.vat as vat, " +
            "rc.order.receiveTime  as receiveTime" +
            " from Receipt  rc join rc.order.cartItems ct where ct.productDetail.product.shop.user.name=:email")
    Optional<List<ReceiptRes>> getAllBuy(@Param("email") String email);
}
