package com.example.application;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.application.data.ActivationRecord;
import com.example.application.data.Order;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.entity.User;
import com.example.application.data.repository.ActivationRecordRepository;
@Service
public class ActivationRecordService {
	@Autowired
	private ActivationRecordRepository repo;
	@Autowired
	private EntityManager em;

	public void activationRecordCreate ( ActivationRecord actRec) {
		repo.save(actRec);
		return;
	}
	
	@Transactional
	public void orderToSchedules(Order order) {
		if(order.getOptionalProducts().isEmpty() == false) {
			order.getOptionalProducts().forEach(e -> activationRecordCreate(new ActivationRecord(order.getUser().getId(), order.getStartDate(), order.getStartDate().plusMonths(order.getTimePurchased()),
					ServiceProductEnum.PRODUCT, e.getId())));
		}
		
		servicePackageToSchedules(order.getServPack(), order);
	}
	@Transactional
	public void servicePackageToSchedules(ServicePackage sp, Order order) {
		if(sp.getOptionalProducts().isEmpty()== false) {
			sp.getOptionalProducts().forEach(e ->activationRecordCreate(new ActivationRecord(order.getUser().getId(), order.getStartDate(), order.getStartDate().plusMonths(order.getTimePurchased()),
					ServiceProductEnum.PRODUCT, e.getId())));
		}

		
		sp.getServiceSet().forEach(e ->activationRecordCreate(new ActivationRecord(order.getUser().getId(), order.getStartDate(), order.getStartDate().plusMonths(order.getTimePurchased()),
				ServiceProductEnum.SERVICE, e.getId())));
	}
	
	
}
