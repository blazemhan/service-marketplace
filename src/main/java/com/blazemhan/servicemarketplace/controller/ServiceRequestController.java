package com.blazemhan.servicemarketplace.controller;

import com.blazemhan.servicemarketplace.entity.ServiceRequest;
import com.blazemhan.servicemarketplace.service.ServiceRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }
    @PostMapping("/send/{serviceId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> requestService(@PathVariable Long serviceId, @RequestBody String description) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        serviceRequestService.createRequest(serviceId, email, description);
        return new ResponseEntity<>("Request sent successfully", HttpStatus.CREATED);
    }
    @GetMapping("/my")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<ServiceRequest>> getMyRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        List<ServiceRequest> requests = serviceRequestService.getMyRequests(email);
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/provider/requests")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<List<ServiceRequest>> getProviderRequests(Authentication auth) {
        return ResponseEntity.ok(serviceRequestService.getRequestsForProvider(auth.getName()));
    }
    @PostMapping("/{requestId}/confirm")
    @PreAuthorize("hasRole('PROVIDER')")
    public ResponseEntity<?> confirm(@PathVariable Long requestId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        serviceRequestService.confirmRequest(requestId,email);
        return new ResponseEntity<>("Request confirmed", HttpStatus.OK);

    }
    @PostMapping("/{requestId}/cancel")
    @PreAuthorize("hasAnyRole('PROVIDER','CUSTOMER')")
    public ResponseEntity<?> cancel(@PathVariable Long requestId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        serviceRequestService.cancelRequest(requestId,email);

        return new ResponseEntity<>("Request cancelled", HttpStatus.OK);
    }

}
