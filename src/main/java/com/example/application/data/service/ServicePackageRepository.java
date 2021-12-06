package com.example.application.data.service;

import com.example.application.data.entity.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer> {

}