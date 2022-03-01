package com.example.application.data.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.Service.MyService;
import com.example.application.data.Service.ServicePackage;

public interface ServicePackageRepository extends JpaRepository<ServicePackage,Long>{
	Optional<ServicePackage> findById(Long id);
}
