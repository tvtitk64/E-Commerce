package com.team2.fsoft.Ecommerce.dto.response;

import java.time.LocalDate;

public interface ReceiptRes {
    public String getProductName();
    public String getShopName();
    public Integer getPrice();

    public Integer getAmount();
    public Integer getPayment();
    public Integer getVat();
    public LocalDate getReceiveTime();
}
