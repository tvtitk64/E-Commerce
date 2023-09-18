package com.team2.fsoft.Ecommerce.repository;


import com.team2.fsoft.Ecommerce.entity.Color;
import com.team2.fsoft.Ecommerce.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size,Long> {
    @Query(value = "SELECT sz FROM Size sz WHERE sz.code= :code")
    Optional<Size> findByCode(@Param("code") String code);
}
