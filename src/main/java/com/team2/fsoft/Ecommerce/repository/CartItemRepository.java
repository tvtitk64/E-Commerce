package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    @Query(value = "SELECT * FROM item_cart WHERE shopping_cart_id= ?1", nativeQuery = true)
    Optional<List<CartItem>> findShoppingCartId(long shopping_cart_id);
}
