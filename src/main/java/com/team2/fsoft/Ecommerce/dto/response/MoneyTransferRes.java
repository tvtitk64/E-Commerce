package com.team2.fsoft.Ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferRes {
    public long TRANSFER_ID;
    public String FROM_USER;
    public String TO_USER;
    public Date TRANSFERED_DATE;
    public  int amount;
}
