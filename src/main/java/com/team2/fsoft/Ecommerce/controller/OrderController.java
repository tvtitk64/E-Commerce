package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.request.OrderReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.service.OrderService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
   private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    MessagesResponse Order(@RequestBody  List<OrderReq> orders) {
        MessagesResponse ms = new MessagesResponse();
        try {
           for (OrderReq order: orders) {
               orderService.Save(order);
           }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = e.getMessage();
        }
        return ms;
    }
    @GetMapping("/GetAllOfShipper")
    @PreAuthorize("hasAuthority('SHIPPER')")
    MessagesResponse GetAllOfShipper() {
        return  orderService.GetAllOfShipper();
    }

    @GetMapping("/GetAll")
    MessagesResponse GetAll() {
        return  orderService.GetAll();
    }

    @GetMapping("/GetAllOfUser")
    @PreAuthorize("hasAuthority('USER')")
    MessagesResponse GetAllOfUser() {
        return  orderService.GetAllOfUser();
    }

    @GetMapping("/GetAllOfShop")
    @PreAuthorize("hasAuthority('SHOPPER')")
    MessagesResponse GetAllOfShop() {
        return  orderService.GetAllOfShop();
    }

    @PostMapping("/cancel/{id}")
    MessagesResponse Cancel(@PathVariable @Positive Long id) {
        return orderService.cancel(id);
    }

    @PostMapping("/approve/{id}")
    MessagesResponse Approve(@PathVariable @Positive Long id) {
        return orderService.approve(id);
    }

    @PostMapping("/findShipper/{id}")
    MessagesResponse FindShipper(@PathVariable @Positive Long id) {
        return orderService.findShipper(id);
    }

    @PostMapping("/cancelShipperWatting/{id}")
    MessagesResponse CancelShipperWatting(@PathVariable @Positive Long id) {
        return orderService.cancelShipperWatting(id);
    }

    @PostMapping("/pickOrder/{id}")
    MessagesResponse PickOrder(@PathVariable @Positive Long id) {
        return orderService.pickOrder(id);
    }

    @PostMapping("/receiveOrder/{id}")
    MessagesResponse receiveOrder(@PathVariable @Positive Long id) {
        return orderService.receiveOrder(id);
    }

    @PostMapping("/done/{id}")
    MessagesResponse DoneOrder(@PathVariable @Positive Long id) {
        return orderService.DoneOrder(id);
    }

    @PostMapping("/sendBack/{id}")
    MessagesResponse SendBackOrder(@PathVariable @Positive Long id) {
        return orderService.SendBackOrder(id);
    }

    @PostMapping("/shipperCancel/{id}")
    MessagesResponse ShipperCancel(@PathVariable @Positive Long id) {
        return orderService.ShipperCancel(id);
    }

    @PostMapping("/approveSendBack/{id}")
    MessagesResponse ApproveSendBack(@PathVariable @Positive Long id) {
        return orderService.ApproveSendBack(id);
    }
}
