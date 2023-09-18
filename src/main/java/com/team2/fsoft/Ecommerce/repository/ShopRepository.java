package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    @Query(value = "SELECT s FROM Shop s WHERE s.user.email= :email")
    Optional<Shop> findByUserEmail(@Param("email") String email);
}
