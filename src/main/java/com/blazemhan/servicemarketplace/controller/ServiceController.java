package com.blazemhan.servicemarketplace.controller;

import com.blazemhan.servicemarketplace.entity.Service;
import com.blazemhan.servicemarketplace.entity.User;
import com.blazemhan.servicemarketplace.repository.UserRepository;
import com.blazemhan.servicemarketplace.service.ServiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/services")
public class ServiceController {

    private final ServiceService serviceService;


    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
    @PostMapping("/provider/create")
    public ResponseEntity<Service> createService(@Valid @RequestBody Service service) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

      return new ResponseEntity<>(serviceService.create(service,email), HttpStatus.CREATED);


        }
    @GetMapping("/all")
    public ResponseEntity<List<Service>> getService(){
        return ResponseEntity.ok(serviceService.getAll());





    }
}
