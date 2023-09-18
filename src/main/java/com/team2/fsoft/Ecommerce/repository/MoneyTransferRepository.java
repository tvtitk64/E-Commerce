package com.team2.fsoft.Ecommerce.repository;


import com.team2.fsoft.Ecommerce.entity.MoneyTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MoneyTransferRepository extends JpaRepository<MoneyTransfer,Long> {
    @Query("SELECT mt FROM MoneyTransfer mt WHERE mt.fromUser.email = :email OR mt.toUser.email = :email ORDER BY mt.id")
    Page<MoneyTransfer> getMe(@Param("email") String email, Pageable pageable);

}
