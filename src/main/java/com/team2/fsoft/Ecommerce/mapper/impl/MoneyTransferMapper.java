package com.team2.fsoft.Ecommerce.mapper.impl;



import com.team2.fsoft.Ecommerce.dto.response.MoneyTransferRes;
import com.team2.fsoft.Ecommerce.entity.MoneyTransfer;
import com.team2.fsoft.Ecommerce.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;


public class MoneyTransferMapper implements Mapper<MoneyTransfer, MoneyTransferRes> {
    @Override
    public MoneyTransfer toEntity(MoneyTransferRes dto) {
        return null;
    }

    @Override
    public MoneyTransferRes toDTO(MoneyTransfer entity) {
        MoneyTransferRes moneyTransferRes = new MoneyTransferRes();
        moneyTransferRes.TRANSFER_ID = entity.getId();
        moneyTransferRes.FROM_USER=entity.getFromUser().getEmail();
        moneyTransferRes.TO_USER= entity.getToUser().getEmail();
        moneyTransferRes.TRANSFERED_DATE=entity.getCreated();
        moneyTransferRes.amount = entity.getAmount();

        return moneyTransferRes;
    }

    @Override
    public List<MoneyTransferRes> toDTOList(List<MoneyTransfer> entityList) {
      return entityList.stream().map(entity->toDTO(entity)).collect(Collectors.toList());
    }

    @Override
    public List<MoneyTransfer> toEntityList(List<MoneyTransferRes> dtoList) {
        return null;
    }
}
