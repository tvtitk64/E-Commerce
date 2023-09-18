package com.team2.fsoft.Ecommerce.security;

import com.team2.fsoft.Ecommerce.constant.ExceptionMessage;
import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.exception.EmailNotFoundException;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return  new UserDetail(userOptional.get());
        } else {
            throw  new EmailNotFoundException(ExceptionMessage.EMAIL_NOT_FOUND);
        }

    }
}
