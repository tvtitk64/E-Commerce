package com.team2.fsoft.Ecommerce.controller;

import com.team2.fsoft.Ecommerce.constant.ExceptionMessage;
import com.team2.fsoft.Ecommerce.dto.request.ConfirmOTP;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.request.ResetPasswordRequest;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.service.UserService;
import com.team2.fsoft.Ecommerce.service.impl.EmailService;
import com.team2.fsoft.Ecommerce.service.impl.OTPService;
import com.team2.fsoft.Ecommerce.service.impl.PasswordResetService;
import com.team2.fsoft.Ecommerce.utils.MyCache;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;
    @Autowired
    OTPService otpService;
    @Autowired
    EmailService emailService;
    @Autowired
    MyCache myCache;

    @Autowired
    PasswordResetService passwordResetService;

    @PostMapping("/generateOtp")
    public MessagesResponse generateOTP(@RequestBody @Valid RegisterReq registerReq) {
        MessagesResponse ms = new MessagesResponse();
        ms.message="Sent";
        String email = registerReq.getEmail();
        int otp = otpService.generateOTP(email);
        //Generate The Template to send OTP

        String message = "Your OTP verified number is: " + otp;
        try {
            if (userService.findByEmail(registerReq.getEmail())!=null) {
                ms.code = 404;
                ms.message = "Your email has been used!";
            } else {
                myCache.saveToCache(registerReq.getEmail(),registerReq);
            }
            emailService.sendOtpMessage(registerReq.getEmail(), "OTP verified code", message);
        } catch (Exception e) {
            ms.code = HttpStatus.NOT_ACCEPTABLE.value();
            ms.message = e.getMessage();
        }
        return ms;
    }

    @PostMapping("/validateOtp")
    public MessagesResponse validateOtp(@RequestBody @Valid ConfirmOTP confirmOTP) {
        final String SUCCESS = "Register Successfully!";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        MessagesResponse ms = new MessagesResponse();
        ms.message = SUCCESS;
        String email = confirmOTP.getEmail();
        RegisterReq registerReq = (RegisterReq) myCache.getFromCache(email);
        myCache.deleteFromCache(email);
        int otpnum = confirmOTP.getOtpNum();
        //Validate the Otp
        if (otpnum >= 0) {
            int serverOtp = otpService.getOtp(email);
            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(email);
                    //Save Account
                    try {
                        User user = userService.create(registerReq);
                    }
                    catch (Exception e ) {
                        ms.code=HttpStatus.INTERNAL_SERVER_ERROR.value();
                        ms.message = ExceptionMessage.CannotRegisterAccount;
                    }
                } else {
                    ms.code=HttpStatus.UNAUTHORIZED.value();
                    ms.message=FAIL;
                }
            } else {
                ms.code= HttpStatus.UNAUTHORIZED.value();
                ms.message=FAIL;
            }
        } else {
            ms.code=HttpStatus.UNAUTHORIZED.value();
            ms.message=FAIL;
        }
        return ms;
    };

    @PostMapping("/forgotPassword")
    public MessagesResponse forgotPassword(@RequestParam @Valid String email) {
        MessagesResponse ms = new MessagesResponse();
        int otp = otpService.generateOTP(email);
        String message = "Your OTP verified number is: " + otp;
        try {
            if (userService.findByEmail(email) == null) {
                ms.code = 404;
                ms.message = "Your email doesn't exist!";
            } else {
                emailService.sendOtpMessage(email, "OTP verified code", message);
            }
        } catch (Exception e) {
            ms.code = HttpStatus.NOT_ACCEPTABLE.value();
            ms.message = e.getMessage();
        }
        return ms;
    }

    @PostMapping("/resetPassword")
    public MessagesResponse resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        final String SUCCESS = "Reset Password Successfully!";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        MessagesResponse ms = new MessagesResponse();
        ms.message = SUCCESS;
        int otpnum = request.getOtpNum();
        String email = request.getEmail();
        if (otpnum >= 0) {
            int serverOtp = otpService.getOtp(email);
            if (serverOtp > 0) {
                if (otpnum == serverOtp) {
                    otpService.clearOTP(email);
                    passwordResetService.resetPassword(email, request.getNewPassword());
                } else {
                    ms.code=HttpStatus.UNAUTHORIZED.value();
                    ms.message=FAIL;
                }
            } else {
                ms.code= HttpStatus.UNAUTHORIZED.value();
                ms.message=FAIL;
            }
        } else {
            ms.code=HttpStatus.UNAUTHORIZED.value();
            ms.message=FAIL;
        }
        return ms;
    }

}
