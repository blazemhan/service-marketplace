package com.blazemhan.servicemarketplace.controller;

import com.blazemhan.servicemarketplace.Exceptions.UserAlreadyExistsException;
import com.blazemhan.servicemarketplace.dto.LoginRequest;
import com.blazemhan.servicemarketplace.entity.User;
import com.blazemhan.servicemarketplace.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("customer/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) throws UserAlreadyExistsException {
        authService.registerCustomer(user);
        return new ResponseEntity<>("Registration Successful", HttpStatus.CREATED);
    }

    @PostMapping("provider/register")
    public ResponseEntity<String> registerProvider(@RequestBody User user) throws UserAlreadyExistsException {
        authService.registerProvider(user);
        return new ResponseEntity<>("Registration Successful", HttpStatus.CREATED);
    }

    @PostMapping("admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody User user) {
        authService.registerAdmin(user);
        return new ResponseEntity<>("Registration Successful", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        authService.deleteUser(id);
        return new ResponseEntity<>("User Deleted Successfully", HttpStatus.OK);
    }
}
