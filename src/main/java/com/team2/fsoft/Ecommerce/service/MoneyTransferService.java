package com.team2.fsoft.Ecommerce.service;


import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.entity.MoneyTransfer;

import java.util.List;

public interface MoneyTransferService {
   void save(MoneyTransfer moneyTransfer);

    List<MoneyTransfer> getMe( ApiParameter apiParameter);
}
