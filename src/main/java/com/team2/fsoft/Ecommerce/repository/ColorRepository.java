package com.team2.fsoft.Ecommerce.repository;


import com.team2.fsoft.Ecommerce.entity.Category;
import com.team2.fsoft.Ecommerce.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color,Long> {
    @Query(value = "SELECT col FROM Color col WHERE col.code= :code")
    Optional<Color> findByCode(@Param("code") String code);
}
