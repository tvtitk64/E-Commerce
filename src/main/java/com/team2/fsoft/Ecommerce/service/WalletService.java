package com.team2.fsoft.Ecommerce.service;


import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;

public interface WalletService {
    public MessagesResponse ToUp(int money) ;

   public MessagesResponse Withdraw(int money);

   public  int getBalance();
   public  MessagesResponse PlusToUser(int money, long userId);
}
