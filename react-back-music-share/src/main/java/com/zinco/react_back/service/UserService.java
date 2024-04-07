package com.zinco.react_back.service;

import com.zinco.react_back.entity.User;
import com.zinco.react_back.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public boolean userIdExists(String userId) {
        return userRepository.existsByUserId(userId);
    }

    public User registerUser(User user) throws ConstraintViolationException {
        if (userIdExists(user.getUserId())) {
            throw new ConstraintViolationException("User ID already exists", null, "UK_8qtpnv06elxuryeuv1ac4ximm");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return entityManager.merge(user); // Persist and return the user
    }

    public User loginUser(String userId) {
        if (userRepository.findByUserId(userId) == null) {
            System.out.println("User not found");
            return null;
        }
        return userRepository.findByUserId(userId);
    }

    public User getUserById(String userId) {
        return userRepository.findByUserId(userId);
    }
}
