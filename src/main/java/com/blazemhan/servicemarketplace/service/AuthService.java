package com.blazemhan.servicemarketplace.service;

import com.blazemhan.servicemarketplace.Exceptions.UserAlreadyExistsException;
import com.blazemhan.servicemarketplace.customs.CustomUserDetails;
import com.blazemhan.servicemarketplace.dto.LoginRequest;
import com.blazemhan.servicemarketplace.entity.Role;
import com.blazemhan.servicemarketplace.entity.User;
import com.blazemhan.servicemarketplace.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public void registerCustomer(User user) throws UserAlreadyExistsException {
        Optional<User> registered = userRepository.findByEmail(user.getEmail());
        if (registered.isPresent()) {
            throw new UserAlreadyExistsException("Customer already exists. " +
                    "Login to access your Account");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

    }

    public void registerProvider(User user) throws UserAlreadyExistsException {
        Optional<User> registered = userRepository.findByEmail(user.getEmail());
        if (registered.isPresent()) {
            throw new UserAlreadyExistsException("Service Provider already exists, " +
                    "Login to access your account");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.PROVIDER);
        userRepository.save(user);

    }

    public String registerAdmin(User user) {
        Optional<User> registered = userRepository.findByEmail(user.getEmail());
        if (registered.isPresent()) {
            throw new RuntimeException("Admin already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);

        return "Admin registered successfully";
    }

    public String authenticate(LoginRequest request) {
        long start = System.currentTimeMillis();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );
        System.out.println("AuthManager.authenticate: " + (System.currentTimeMillis() - start) + "ms");

        if (authentication.isAuthenticated()) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            long tokenStart = System.currentTimeMillis();
            User user = userDetails.getUser();  // <-- If slow, issue is here
            String token = jwtService.generateToken(user);
            System.out.println("Token gen + getUser: " + (System.currentTimeMillis() - tokenStart) + "ms");

            System.out.println("TOTAL login time: " + (System.currentTimeMillis() - start) + "ms");

            return token;
        }


        return "Wrong Email or Password";
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
