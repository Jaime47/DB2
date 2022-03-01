package com.example.application;


import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.repository.OptionalProductRepository;
import com.example.application.data.repository.ServiceRepository;


@Service
public class OptionalProductService {
	
	@Autowired
	private OptionalProductRepository repo;
	@Autowired
	private EntityManager em;

	public void optionalProductCreate (OptionalProduct serv) {
		repo.save(serv);
		return;
	}
	
	
}