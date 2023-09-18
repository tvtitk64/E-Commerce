package com.team2.fsoft.Ecommerce.controller;


import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ChangePasswordRequest;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/updateUser")
    public MessagesResponse updateUserInfo( @RequestBody @Valid RegisterReq registerReq) {
        MessagesResponse messagesResponse = new MessagesResponse();
        userService.updateUserInformation(registerReq);
        return messagesResponse;
    }

    @DeleteMapping("/deleteUser")
    public MessagesResponse deleteUser(@RequestParam long id) {
        MessagesResponse messagesResponse = new MessagesResponse();
        userService.deleteUser(id);
        return messagesResponse;
    }

    @PostMapping("/changePassword")
    public MessagesResponse changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }
    @PostMapping("/api/search")
    public PageDTO<UserRes> GetLists(@RequestBody ApiParameter apiParameter){
        return  userService.getLists(apiParameter);
    }
}
