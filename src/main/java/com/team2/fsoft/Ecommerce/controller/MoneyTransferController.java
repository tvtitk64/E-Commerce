package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.MoneyTransferRes;
import com.team2.fsoft.Ecommerce.entity.MoneyTransfer;
import com.team2.fsoft.Ecommerce.mapper.impl.MoneyTransferMapper;
import com.team2.fsoft.Ecommerce.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/moneyTransfer")
public class MoneyTransferController {
    private final MoneyTransferService moneyTransferService;

    public MoneyTransferController(MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    @PostMapping("/getMe")
    public MessagesResponse GetMe(@RequestBody ApiParameter apiParameter) {
        MessagesResponse ms = new MessagesResponse();
        List<MoneyTransfer> lst = moneyTransferService.getMe(apiParameter);
        MoneyTransferMapper mapper = new MoneyTransferMapper();
        ms.data = mapper.toDTOList(lst);
        return ms;

    }

}
