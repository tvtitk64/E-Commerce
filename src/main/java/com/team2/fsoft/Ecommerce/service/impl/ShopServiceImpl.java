package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.ShopDTO;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.Shop;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.mapper.impl.ShopMapper;
import com.team2.fsoft.Ecommerce.repository.ShopRepository;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.ShopService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {
    final ShopRepository shopRepository;
    final UserRepository userRepository;
    final ShopMapper shopMapper;

    public ShopServiceImpl(ShopRepository shopRepository, UserRepository userRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.shopMapper = shopMapper;
    }

    @Override
    public MessagesResponse save(ShopDTO shopDTO) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User userAccount = userRepository.findByEmail(email).get();
            Shop shop = shopMapper.toEntity(shopDTO);
            shop.setUser(userAccount);
            shopRepository.save(shop);
        }
        catch (Exception e) {
            ms.code=400;
            ms.message="Shop Action Failed";
        }
        return  ms;
    }

    @Override
    public MessagesResponse getInfo() {
        MessagesResponse ms = new MessagesResponse();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        var shopOptional = shopRepository.findByUserEmail(email);
        if (shopOptional.isPresent()) {
            var shop = shopOptional.get();
            var shopRes = shopMapper.toDTO(shop);
            ms.data= shopRes;

        } else  {
            ms.code=404;
            ms.message="Không tìm thấy thông tin cửa hàng, vui lòng thử lại!";
        }
        return  ms;
    }
}

