package com.team2.fsoft.Ecommerce.service;

import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ChangePasswordRequest;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.repository.UserRepository;

import java.util.List;

public interface UserService {
    User create(RegisterReq registerReq);

    MessagesResponse changePassword(ChangePasswordRequest changePasswordRequest);

    void updateUserInformation(RegisterReq registerReq);

    void deleteUser(long id);

    User findByEmail(String email);


    PageDTO<UserRes> getLists(ApiParameter apiParameter);

    User findById(long Id);
}