package com.team2.fsoft.Ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CReceiptRes {
    public String productName;
    public String shopName;
    public Integer price;
    public Integer amount;
    public Integer payment;
    public Integer vat;
    public LocalDate receiveTime;
}
