package com.blazemhan.servicemarketplace.service;

import com.blazemhan.servicemarketplace.entity.ServiceRequest;
import com.blazemhan.servicemarketplace.entity.Status;
import com.blazemhan.servicemarketplace.entity.User;
import com.blazemhan.servicemarketplace.repository.ServiceRepository;
import com.blazemhan.servicemarketplace.repository.ServiceRequestRepository;
import com.blazemhan.servicemarketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;


    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    public void createRequest(Long serviceId, String email, String description) {

        User customer = userRepository.findByEmail(email).orElseThrow(()->
                new RuntimeException("User not found"));

        com.blazemhan.servicemarketplace.entity.Service service = serviceRepository.
                findById(serviceId).orElseThrow(()-> new RuntimeException("Service not found"));
        User provider = service.getOwner();

        ServiceRequest request = new ServiceRequest();
        request.setCustomer(customer);
        request.setProvider(provider);
        request.setService(service);
        request.setDescription(description);
        request.setRequestedAt(LocalDateTime.now());
        request.setStatus(Status.PENDING);

        serviceRequestRepository.save(request);

    }


    public List<ServiceRequest> getMyRequests(String email) {
        return serviceRequestRepository.findAllByCustomer_Email(email);
    }

    public List<ServiceRequest> getRequestsForProvider(String providerEmail) {
        return serviceRequestRepository.findAllByProvider_Email(providerEmail);
    }

    public void confirmRequest(Long requestId, String email) throws AccessDeniedException {
        ServiceRequest request = serviceRequestRepository.findById(requestId).
                orElseThrow(()-> new RuntimeException("Request not found"));

//        if(!request.getService().getOwner().getEmail().equals(email)) {
//            throw new AccessDeniedException("You are not authorized to perform this action");
//        }



        if(!request.getProvider().getEmail().equals(email)){
            throw new AccessDeniedException("You are not authorized to perform this action");
        }

        request.setStatus(Status.ACCEPTED);
        serviceRequestRepository.save(request);
    }
    public void cancelRequest(Long requestId, String email) throws AccessDeniedException {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(()-> new RuntimeException("Request not found"));


        if(!request.getProvider().getEmail().equals(email) &&  !request.getCustomer().getEmail().equals(email)){
            throw new AccessDeniedException("You are not authorized to perform this action");
        }


        request.setStatus(Status.REJECTED);
        serviceRequestRepository.save(request);
    }
}
