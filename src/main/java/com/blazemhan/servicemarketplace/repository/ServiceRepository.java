package com.blazemhan.servicemarketplace.repository;

import com.blazemhan.servicemarketplace.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    //List<Service> findServiceByUserId(Long userId);

}