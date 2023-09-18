package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "select product.* from product " +
            "where product.name = ?1", nativeQuery = true)
    Optional<Product> findByName(String productName);
}
