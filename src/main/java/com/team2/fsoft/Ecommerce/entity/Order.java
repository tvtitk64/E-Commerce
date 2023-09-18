package com.team2.fsoft.Ecommerce.entity;

import com.team2.fsoft.Ecommerce.constant.OrderStatus;
import com.team2.fsoft.Ecommerce.enum_constant.PaymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {
    @Column(name= "receive_address")
    private  String receiveAddress;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private int status = OrderStatus.APPROVE_WAITING;

    @Column(name = "receive_time")
    private LocalDate receiveTime;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order",cascade = CascadeType.PERSIST)
    private List<CartItem> cartItems;

    @ManyToOne
    @JoinColumn(name = "ship_fee_id")
    private ShipFee shipFee;
}
