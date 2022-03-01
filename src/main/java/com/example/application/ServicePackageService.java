package com.example.application;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Service.MyService;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.repository.ServicePackageRepository;
import com.example.application.data.repository.ServiceRepository;

@Service
public class ServicePackageService{
	
	@Autowired
	private ServicePackageRepository repo;
	@Autowired
	private EntityManager em;

	public void servicePackageCreate (ServicePackage pack) {
		repo.save(pack);
		return;
	}
	
	
}
