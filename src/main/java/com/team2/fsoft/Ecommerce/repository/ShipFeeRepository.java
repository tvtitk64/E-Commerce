package com.team2.fsoft.Ecommerce.repository;


import com.team2.fsoft.Ecommerce.entity.ShipFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ShipFeeRepository extends JpaRepository<ShipFee,Long> {
    @Query(value = "SELECT sf FROM ShipFee sf WHERE sf.code= :code")
    Optional<ShipFee> findByCode(@Param("code") String code);
}
