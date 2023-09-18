package com.team2.fsoft.Ecommerce.service.impl;

import com.team2.fsoft.Ecommerce.entity.User;
import com.team2.fsoft.Ecommerce.repository.UserRepository;
import com.team2.fsoft.Ecommerce.utils.MyCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetService {
    @Autowired
    private UserRepository userRepository;

    public void resetPassword(String email, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(newPassword);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            userOptional.get().setPassword(password);
            userRepository.save(userOptional.get());
        }
    }
}
