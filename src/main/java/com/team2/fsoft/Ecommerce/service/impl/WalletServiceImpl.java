package com.team2.fsoft.Ecommerce.service.impl;


import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.MoneyTransfer;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.entity.Wallet;
import com.team2.fsoft.Ecommerce.repository.WalletRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.MoneyTransferService;
import com.team2.fsoft.Ecommerce.service.UserService;
import com.team2.fsoft.Ecommerce.service.WalletService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final MoneyTransferService moneyTransferService;
    private final UserService userService;
    @Value("${payment_account_id}")
    private  long payment_account_id;

    public WalletServiceImpl(WalletRepository walletRepository, MoneyTransferService moneyTransferService, UserService userService) {
        this.walletRepository = walletRepository;
        this.moneyTransferService = moneyTransferService;
        this.userService = userService;
    }


    @Override
    @Transactional
    public MessagesResponse ToUp(int money) {
        MessagesResponse ms = new MessagesResponse();
        Wallet paymentWallet = walletRepository.findById(payment_account_id).get();
        User adminAccount = userService.findById(payment_account_id);
        if (paymentWallet != null) {
      if (money<=paymentWallet.getMoney()) {

          Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
          String email = authentication.getName();

          Wallet userWallet = walletRepository.findByUserEmail(email).get();
          try {
              var userAccount = userService.findByEmail(email);
              paymentWallet.subMoney(money);
              userWallet.addMoney(money);
              walletRepository.save(paymentWallet);
              walletRepository.save(userWallet);
              MoneyTransfer moneyTransfer = new MoneyTransfer();
              moneyTransfer.setAmount(money);
              moneyTransfer.setToUser(userAccount);
              moneyTransfer.setFromUser(adminAccount);
              moneyTransferService.save(moneyTransfer);
          }
          catch (Exception e) {
              ms.code = 500;
              ms.message="Nạp tiền không thành công. Vui lòng thử lại!";
          }

      } else {
          ms.code=500;
          ms.message="Tạm thời chưa thể nạp thêm tiền do số dư tài khoản hệ thông không đủ!";
      }
        }
return ms;
    }


    @Override
    @Transactional
    public MessagesResponse Withdraw(int money) {
        MessagesResponse ms = new MessagesResponse();
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Wallet userWallet = walletRepository.findByUserEmail(email).get();
        User adminAccount = userService.findById(payment_account_id);
        User userAccount = userService.findByEmail(email);
        if (userWallet!=null) {

            if (userWallet.getMoney()>=money) {
                Wallet paymentWallet = walletRepository.findByUserId(payment_account_id).get();

                try {
                    userWallet.subMoney(money);
                    paymentWallet.addMoney(money);
                    walletRepository.save(paymentWallet);
                    walletRepository.save(userWallet);
                    MoneyTransfer moneyTransfer = new MoneyTransfer();
                    moneyTransfer.setAmount(money);
                    moneyTransfer.setToUser(adminAccount);
                    moneyTransfer.setFromUser(userAccount);
                    moneyTransferService.save(moneyTransfer);
                }
                catch (Exception ex) {
                    ms.code = 500;
                    throw new RuntimeException(ex.getMessage());
                }
            }
            else {
                ms.code=500;
                ms.message="Số dư khả dụng của quý khách hiện tại không đủ!";
            }
        }



        return ms;
    }

    @Override
    public int getBalance() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Wallet userWallet = walletRepository.findByUserEmail(email).get();
        if (userWallet == null) {
            throw  new RuntimeException("Wallet is not found!");
        }
        return userWallet.getMoney();
    }

    @Override
    @Transactional
    public MessagesResponse PlusToUser(int money, long userId) {
        MessagesResponse ms = new MessagesResponse();
        Wallet paymentWallet = walletRepository.findByUserId(payment_account_id).get();
        User adminAccount = userService.findById(payment_account_id);
        if (paymentWallet != null) {
            if (money<=paymentWallet.getMoney()) {


                Wallet userWallet = walletRepository.findByUserId(userId).get();
                try {
                    var userAccount = userService.findById(userId);
                    paymentWallet.subMoney(money);
                    userWallet.addMoney(money);
                    walletRepository.save(paymentWallet);
                    walletRepository.save(userWallet);
                    MoneyTransfer moneyTransfer = new MoneyTransfer();
                    moneyTransfer.setAmount(money);
                    moneyTransfer.setToUser(userAccount);
                    moneyTransfer.setFromUser(adminAccount);
                    moneyTransferService.save(moneyTransfer);
                }
                catch (Exception e) {
                    ms.code = 500;
                    ms.message="Chuyển tiền không thành công. Vui lòng thử lại!";
                }

            } else {
                ms.code=500;
                ms.message="Tạm thời chưa thể nạp thêm tiền do số dư tài khoản hệ thông không đủ!";
            }
        }
        return ms;
    }
}
