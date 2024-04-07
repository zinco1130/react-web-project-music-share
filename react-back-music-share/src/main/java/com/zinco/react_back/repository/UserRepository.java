package com.zinco.react_back.repository;

import com.zinco.react_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByUserId(String userId);

    Optional<User> findByUserIdAndPassword(String userId, String password);

    User findByUserId(String username);
}
