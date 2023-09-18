package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReq {
    public List<Long> cartItemIds;
    public  String paymentCode;
    public String shipCode;
    public  String receiveAddress;
}
