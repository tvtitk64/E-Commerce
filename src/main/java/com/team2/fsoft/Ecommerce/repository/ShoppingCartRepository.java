package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {


    @Query(value = "SELECT sc FROM ShoppingCart sc WHERE sc.user.email= :email")
    Optional<ShoppingCart> findByUserEmail(@Param("email") String email);
}
