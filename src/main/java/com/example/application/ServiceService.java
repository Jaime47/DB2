package com.example.application;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Service.MyService;
import com.example.application.data.repository.ServiceRepository;


@Service
public class ServiceService{
	
	@Autowired
	private ServiceRepository repo;
	@Autowired
	private EntityManager em;

	public void serviceCreate (MyService serv) {
		repo.save(serv);
		return;
	}
	
	
}