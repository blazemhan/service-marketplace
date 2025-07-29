package com.blazemhan.servicemarketplace.repository;

import com.blazemhan.servicemarketplace.entity.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    List<ServiceRequest> findAllByCustomer_Email(String customerEmail);
    List<ServiceRequest> findAllByProvider_Email(String email);

}