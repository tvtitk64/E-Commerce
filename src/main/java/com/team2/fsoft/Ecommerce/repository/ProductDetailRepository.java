package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.Product;
import com.team2.fsoft.Ecommerce.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail,Long> {
}
