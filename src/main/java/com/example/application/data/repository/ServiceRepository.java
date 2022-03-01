package com.example.application.data.repository;

import com.example.application.data.*;
import com.example.application.data.Service.FixedInternetService;
import com.example.application.data.Service.MyService;
import com.example.application.data.Service.ServiceType;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<MyService, Long> {
	Optional<MyService> findById(Long id);
	Collection<MyService> findByServiceType(ServiceType st);
}