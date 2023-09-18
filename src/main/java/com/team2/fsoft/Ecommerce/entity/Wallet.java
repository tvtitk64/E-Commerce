package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    private long id;
    private int money;
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public void addMoney(Integer money) {
        this.money += money;
    }

    public void subMoney(Integer money) {
        this.money -= money;
    }

    public Wallet(User user) {
        this.user = user;
        this.money = 0;
    }
}