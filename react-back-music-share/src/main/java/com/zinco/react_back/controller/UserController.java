package com.zinco.react_back.controller;

import com.zinco.react_back.entity.Comment;
import com.zinco.react_back.entity.Tab;
import com.zinco.react_back.entity.User;
import com.zinco.react_back.service.CommentService;
import com.zinco.react_back.service.TabService;
import com.zinco.react_back.service.UserService;
import com.zinco.react_back.utils.GeneralResponse;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TabService tabService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> registerUser(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        try {
            String userPW = user.getPassword();
            userService.registerUser(user);
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                    user.getUserId(), userPW);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return ResponseEntity.ok().body(new RegistrationResponse(true, "Registration successful"));
        } catch (ConstraintViolationException e) {
            logger.error("Registration failed due to constraint violation: ", e);
            return ResponseEntity.status(400).body(new RegistrationResponse(false, e.getMessage()));
        } catch (Exception e) {
            logger.error("Registration failed: ", e);
            return ResponseEntity.status(500).body(new RegistrationResponse(false, "Registration failed: " + e.getMessage()));
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RegistrationResponse {
        private boolean success;
        private String message;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                    loginRequest.getUserId(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authentication);
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            HttpSession session = request.getSession(false); // false means don't create a new session if one doesn't exist
            return ResponseEntity.ok().body(new LoginResponse(true, "Login successful", userService.loginUser(loginRequest.getUserId())));
        } catch (Exception e) {
            logger.error("Login failed: ", e);
            return ResponseEntity.status(500).body(new LoginResponse(false, "Login failed: " + e.getMessage(), null));
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String userId;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginResponse {
        private boolean success;
        private String message;
        private User user;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
            securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            return ResponseEntity.ok().body(new GeneralResponse(true, "Logout successful"));
        } catch (Exception e) {
            logger.error("Logout failed: ", e);
            return ResponseEntity.status(500).body(new GeneralResponse(false, "Logout failed: " + e.getMessage()));
        }
    }
}
