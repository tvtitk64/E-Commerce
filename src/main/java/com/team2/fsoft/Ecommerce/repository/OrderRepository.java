package com.team2.fsoft.Ecommerce.repository;


import com.team2.fsoft.Ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
