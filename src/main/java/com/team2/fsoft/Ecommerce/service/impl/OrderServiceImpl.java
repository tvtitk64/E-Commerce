package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.constant.OrderStatus;
import com.team2.fsoft.Ecommerce.dto.request.OrderReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.entity.*;
import com.team2.fsoft.Ecommerce.enum_constant.PaymentType;
import com.team2.fsoft.Ecommerce.repository.*;
import com.team2.fsoft.Ecommerce.service.OrderService;
import com.team2.fsoft.Ecommerce.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;

    final ShipFeeRepository shipFeeRepository;

    final CartItemRepository cartItemRepository;
    final WalletService walletService;

    private final UserRepository userRepository;

    final ReceiptRepository receiptRepository;


    public OrderServiceImpl(OrderRepository orderRepository, ShipFeeRepository shipFeeRepository, CartItemRepository cartItemRepository, WalletService walletService, UserRepository userRepository, ReceiptRepository receiptRepository) {
        this.orderRepository = orderRepository;
        this.shipFeeRepository = shipFeeRepository;
        this.cartItemRepository = cartItemRepository;
        this.walletService = walletService;
        this.userRepository = userRepository;
        this.receiptRepository = receiptRepository;
    }

    @Override
    @Transactional
    public void Save(OrderReq orderReqs) {
        Order order = new Order();
        ShipFee ship = shipFeeRepository.findByCode(orderReqs.shipCode).get();
        PaymentType payment = orderReqs.paymentCode.equals("WALLET") ? PaymentType.WALLET : PaymentType.OFFLINE;
        List<CartItem> cartItems = orderReqs.cartItemIds.stream().map(cart_id -> cartItemRepository.findById(cart_id).get()).collect(Collectors.toList());
        if (orderReqs.paymentCode.equals("WALLET")) {
            int total = cartItems.stream()
                    .mapToInt(cartItem -> cartItem.getProductDetail().getPrice() * cartItem.getAmount())
                    .sum() + ship.getFee();
            int balance = walletService.getBalance();

            if (walletService.getBalance() < total) {
                throw new RuntimeException("Wallet is not enough!");
            }

            walletService.Withdraw(total);


        }
        order.setCartItems(cartItems);
        order.setPaymentType(payment);
        order.setShipFee(ship);
        order.setReceiveAddress(orderReqs.receiveAddress);
        cartItems.forEach(cartItem -> cartItem.setOrder(order));
        orderRepository.save(order);

    }

    @Override
    public MessagesResponse GetAllOfUser() {
        return null;
    }

    @Override
    @Transactional
    public MessagesResponse cancel(Long orderId) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var orderOptional = orderRepository.findById(orderId);
            if (orderOptional.isPresent()) {
                var order = orderOptional.get();
                if (order.getStatus() < OrderStatus.SHIPPING) {


                    List<CartItem> cartItems = order.getCartItems();
                    if (order.getPaymentType() == PaymentType.WALLET) {
                        int total = cartItems.stream()
                                .mapToInt(cartItem -> cartItem.getProductDetail().getPrice() * cartItem.getAmount())
                                .sum() + order.getShipFee().getFee();
                        walletService.ToUp(total);
                    }
                    ShoppingCart shoppingCart = cartItems.get(0).getShoppingCart();
                    shoppingCart.setCount(shoppingCart.getCount() - 1);

                    orderRepository.delete(order);

                }
            } else {
                ms.code = 500;
                ms.message = " Hủy đơn hàng không thành công!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Hủy đơn hàng không thành công!";
        }

        return ms;
    }

    @Override
    public MessagesResponse GetAllOfShop() {
        return null;
    }

    @Override
    public MessagesResponse approve(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                if (order.getStatus() == OrderStatus.APPROVE_WAITING)
                    order.setStatus(OrderStatus.GOODS_PREPARED);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse findShipper(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                var order = orderOptional.get();
                if (order.getStatus() == OrderStatus.GOODS_PREPARED) {
                    order.setStatus(OrderStatus.SHIPPER_WAITING_CONFIRM);
                    orderRepository.save(order);
                }

            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse GetAllOfShipper() {
        return null;
    }

    @Override
    public MessagesResponse GetAll() {
        return null;
    }

    @Override
    public MessagesResponse pickOrder(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                var order = orderOptional.get();
                if (order.getStatus() == OrderStatus.SHIPPER_WAITING_CONFIRM)
                    order.setStatus(OrderStatus.SHIPPER_PICK_WAITING);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    @Transactional
    public MessagesResponse receiveOrder(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                var authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                int total = order.getCartItems().stream()
                        .mapToInt(cartItem -> cartItem.getProductDetail().getPrice() * cartItem.getAmount())
                        .sum();
                walletService.Withdraw(total);

                walletService.PlusToUser(total, order.getCartItems().get(0).getProductDetail().getProduct().getShop().getUser().getId());
                if (order.getStatus() == OrderStatus.SHIPPER_PICK_WAITING)
                    order.setStatus(OrderStatus.SHIPPING);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse DoneOrder(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                User user = userRepository.findByEmail(email).get();
                int total =0;
                if (order.getPaymentType() == PaymentType.WALLET) {
                     total = order.getCartItems().stream()
                            .mapToInt(cartItem -> cartItem.getProductDetail().getPrice() * cartItem.getAmount())
                            .sum() + order.getShipFee().getFee();
                    walletService.PlusToUser(total, user.getId());
                }
                if (order.getStatus() == OrderStatus.SHIPPING)
                    order.setStatus(OrderStatus.DONE);
                order.setReceiveTime(LocalDate.now());
                orderRepository.save(order);
                Receipt receipt = new Receipt();
                receipt.setOrder(order);
                receipt.setPayment(total);
                receiptRepository.save(receipt);

            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse ShipperCancel(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                if (order.getStatus() == OrderStatus.SHIPPING)
                    order.setStatus(OrderStatus.REJECT);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse cancelShipperWatting(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                var order = orderOptional.get();
                if (order.getStatus() == OrderStatus.SHIPPER_WAITING_CONFIRM)
                    order.setStatus(OrderStatus.GOODS_PREPARED);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse SendBackOrder(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            Optional<Order> orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                Order order = orderOptional.get();
                if (order.getStatus() == OrderStatus.DONE)
                    order.setStatus(OrderStatus.SEND_BACK);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }

        return ms;
    }

    @Override
    public MessagesResponse ApproveSendBack(Long id) {
        MessagesResponse ms = new MessagesResponse();
        try {
            var orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                var order = orderOptional.get();

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String email = authentication.getName();
                User user = userRepository.findByEmail(email).get();
                if (order.getPaymentType() == PaymentType.WALLET) {
                    int total = order.getCartItems().stream()
                            .mapToInt(cartItem -> cartItem.getProductDetail().getPrice() * cartItem.getAmount())
                            .sum();
                    walletService.PlusToUser(total, user.getId());
                }

                if (order.getStatus() == OrderStatus.SEND_BACK)
                    order.setStatus(OrderStatus.DONE);
                orderRepository.save(order);
            } else {
                ms.code = 500;
                ms.message = " Không thể xác thực đơn hàng!";
            }
        } catch (Exception e) {
            ms.code = 500;
            ms.message = " Không thể xác thực đơn hàng!";
        }
        return ms;
    }


}
