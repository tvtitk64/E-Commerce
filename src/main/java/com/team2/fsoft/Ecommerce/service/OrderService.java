package com.team2.fsoft.Ecommerce.service;


import com.team2.fsoft.Ecommerce.dto.request.OrderReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;

public interface OrderService {
   void Save(OrderReq orderReqs);

   MessagesResponse GetAllOfUser();

   MessagesResponse cancel(Long orderId);

    MessagesResponse GetAllOfShop();

    MessagesResponse approve(Long id);

    MessagesResponse findShipper(Long id);

    MessagesResponse GetAllOfShipper();

    MessagesResponse GetAll();

    MessagesResponse pickOrder(Long id);

    MessagesResponse receiveOrder(Long id);

    MessagesResponse DoneOrder(Long id);

    MessagesResponse ShipperCancel(Long id);

    MessagesResponse cancelShipperWatting(Long id);

    MessagesResponse SendBackOrder(Long id);

    MessagesResponse ApproveSendBack(Long id);
}
