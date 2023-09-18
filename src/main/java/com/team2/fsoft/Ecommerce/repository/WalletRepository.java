package com.team2.fsoft.Ecommerce.repository;


import com.team2.fsoft.Ecommerce.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    @Query(value = "SELECT wl FROM Wallet wl  WHERE wl.id= :wallet_id")
    Optional<Wallet> findByUserId(@Param("wallet_id") long wallet_id);
    @Query(value = "SELECT wl FROM Wallet wl  WHERE wl.user.email= :user_email")
    Optional<Wallet> findByUserEmail(@Param("user_email") String user_email);
}
