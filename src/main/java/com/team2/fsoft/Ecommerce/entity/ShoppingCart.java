package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private   int count;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "shoppingCart")
    private List<CartItem> cartItemList;

    public ShoppingCart(User user) {
        this.user=user;
        count=0;
    }

}
