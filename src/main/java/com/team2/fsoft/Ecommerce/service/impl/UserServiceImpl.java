package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.dto.PageDTO;
import com.team2.fsoft.Ecommerce.dto.UserDTO;
import com.team2.fsoft.Ecommerce.dto.request.ApiParameter;
import com.team2.fsoft.Ecommerce.dto.request.ChangePasswordRequest;
import com.team2.fsoft.Ecommerce.dto.request.RegisterReq;
import com.team2.fsoft.Ecommerce.dto.response.MessagesResponse;
import com.team2.fsoft.Ecommerce.dto.response.UserRes;
import com.team2.fsoft.Ecommerce.entity.ShoppingCart;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.entity.Wallet;
import com.team2.fsoft.Ecommerce.mapper.impl.UserMapper;
import com.team2.fsoft.Ecommerce.mapper.impl.UserResMapper;
import com.team2.fsoft.Ecommerce.repository.ShoppingCartRepository;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import com.team2.fsoft.Ecommerce.repository.WalletRepository;
import com.team2.fsoft.Ecommerce.security.UserDetail;
import com.team2.fsoft.Ecommerce.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final UserResMapper userResMapper;

    private final WalletRepository walletRepository;

    private final ShoppingCartRepository shoppingCartRepository;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserResMapper userResMapper, WalletRepository walletRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userResMapper =userResMapper;
        this.walletRepository = walletRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public User create(RegisterReq registerReq) {
        User user= userRepository.save(userMapper.toEntity(registerReq));
        Wallet wallet = new Wallet(user);
        walletRepository.save(wallet);
        ShoppingCart shoppingCart = new ShoppingCart(user);
        shoppingCartRepository.save(shoppingCart);
        return user;
    }

    @Override
    public void updateUserInformation(@Valid RegisterReq registerReq) {
        userRepository.save(userMapper.toEntity(registerReq));

    }

    @Override
    public void deleteUser(long id) {

        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new RuntimeException("Email doesn't exist");
    }

    @Override
    public PageDTO<UserRes> getLists(ApiParameter apiParameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        // Filter by text (if provided)
        String searchText = "%" + apiParameter.filter.text + "%";
        Predicate nameLike = criteriaBuilder.like(root.get("name"), searchText);
        Predicate emailLike = criteriaBuilder.like(root.get("email"), searchText);
        predicates.add(criteriaBuilder.or(nameLike, emailLike));

        // Filter by created date (if provided)
        if (apiParameter.filter != null && apiParameter.filter.created != null) {
            predicates.add(criteriaBuilder.equal(root.get("created"), apiParameter.filter.created));
        }

        // Filter by author (if provided)
        if (apiParameter.filter != null && apiParameter.filter.author != null && !apiParameter.filter.author.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("author"), apiParameter.filter.author));
        }
        // Filter by descending and orderBy (if provided)
        if (apiParameter.filter != null && apiParameter.filter.orderBy!=null) {
            if (apiParameter.filter.ascending) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(apiParameter.filter.orderBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(apiParameter.filter.orderBy)));
            }
        }

        if (!predicates.isEmpty()) {

            criteriaQuery.where(predicates.toArray(new Predicate[0]));
        }
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        int totalRows = query.getResultList().size();
        List<User> results = query
                .setFirstResult((apiParameter.page - 1) * apiParameter.limit) // Offset
                .setMaxResults(apiParameter.limit) // Limit
                .getResultList();
        PageDTO<UserRes> userResPageDTO = new PageDTO<>(userResMapper.toDTOList(results),apiParameter.page,totalRows);

            return userResPageDTO;
        }

    @Override
    public User findById(long Id) {
        var userOptional = userRepository.findById(Id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        ;
        return null;
    }

    @Override
    public MessagesResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        MessagesResponse ms = new MessagesResponse();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = (UserDetail) authentication.getPrincipal();
        long userId = user.getId();

        var userAccountOptional = userRepository.findById(userId);
        if (userAccountOptional.isPresent()) {
            var userAccount = userAccountOptional.get();
            if (passwordEncoder.matches(changePasswordRequest.oldPassword, userAccount.getPassword())) {
                String password = passwordEncoder.encode(changePasswordRequest.newPassword);
                userAccount.setPassword(password);
                userRepository.save(userAccount);
            } else {
                ms.code = 400;
                ms.message="Mật khẩu hiện tại bạn nhập không đúng. Vui lòng nhập lại!";
            }
        }
        return  ms;
    }
}
