package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.entity.MoneyTransfer;
import com.team2.fsoft.Ecommerce.repository.MoneyTransferRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MoneyTransferServiceImpl implements MoneyTransferService {
    final  MoneyTransferRepository moneyTransferRepository;

    public MoneyTransferServiceImpl(MoneyTransferRepository moneyTransferRepository) {
        this.moneyTransferRepository = moneyTransferRepository;
    }

    @Override
    public void save(MoneyTransfer moneyTransfer) {
        moneyTransferRepository.save(moneyTransfer);
    }

    @Override
    public List<MoneyTransfer> getMe( ApiParameter apiParameter) {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        int page = apiParameter.page;
        int limit = apiParameter.limit;
        if (page!=0 && limit!=0) {
            Pageable pageable = PageRequest.of(page-1,limit);
            Page<MoneyTransfer> moneyTransferPage = moneyTransferRepository.getMe(email,pageable);
            if (moneyTransferPage != null) {
                List<MoneyTransfer> lst = moneyTransferPage.getContent();
                return  lst;
            }
        }

        return null;
    }
}
