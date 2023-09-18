package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ship_fee")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipFee {
    @Id
    private String code;
    @NotBlank
    private String name;

    private int fee;

}
