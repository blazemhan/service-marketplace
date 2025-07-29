package com.blazemhan.servicemarketplace.service;

import com.blazemhan.servicemarketplace.entity.Service;
import com.blazemhan.servicemarketplace.entity.User;
import com.blazemhan.servicemarketplace.repository.ServiceRepository;
import com.blazemhan.servicemarketplace.repository.UserRepository;
import org.springframework.security.core.Authentication;


import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceService {

    private final ServiceRepository  serviceRepository;
    private final UserRepository userRepository;

    public ServiceService(ServiceRepository serviceRepository, UserRepository userRepository){
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }


    public List<Service> getAll(){
        return serviceRepository.findAll();
    }

    public Service create(Service service, String  email){
        User user = userRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("User Not Found"));

        service.setOwner(user);

       return  serviceRepository.save(service);

    }

    public Service getById(Long id){
        return serviceRepository.findById(id).orElseThrow(()->
                new RuntimeException("Invalid Id"));

    }

    public Service updateService(Long id, Service service, String email){
        Service existingService = serviceRepository.findById(id).orElseThrow(()->
                new RuntimeException("ID not found"));
        if(!existingService.getOwner().getEmail().equals(email)){
            throw new RuntimeException("Unauthorized");
        }

        existingService.setName(service.getName());
        existingService.setDescription(service.getDescription());
        existingService.setPrice(service.getPrice());


      return   serviceRepository.save(existingService);


    }

    public void deleteService(Long id){
          serviceRepository.deleteById(id);

    }


}
