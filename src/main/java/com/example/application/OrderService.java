package com.example.application;

import java.util.Iterator;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.application.data.Order;
import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.entity.User;
import com.example.application.data.repository.OptionalProductRepository;
import com.example.application.data.repository.OrderRepository;
import com.vaadin.flow.component.notification.Notification;
import com.example.application.data.repository.*;
@Service
public class OrderService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private OrderRepository repo;
	@Autowired
	private EntityManager em;
	@Autowired
	private Utils utils;
	@Autowired
	private ActivationRecordService ACS;

	public void orderCreate (Order ord) {
		calculatePrice(ord);
		repo.save(ord);
		return;
	}
	
	public void calculatePrice(Order ord) {
		
		Iterator<OptionalProduct> iterator = ord.getOptionalProducts().iterator();
		float optProductFare = 0;
		//Calculate sum of optional products fares
		while(iterator.hasNext()) {
		OptionalProduct next = iterator.next();	
		optProductFare += next.getPrice();	
		}
		
		if(ord.getTimePurchased() == 12){
			ord.setPrice(12*(optProductFare + ord.getServPack().getTwelveMonths()));
		}else if(ord.getTimePurchased() == 24){
			ord.setPrice(24*(optProductFare + ord.getServPack().getTwentyFourMonths()));
		}else {
			ord.setPrice(36*(optProductFare + ord.getServPack().getThirtySixMonths()));
		}

	}

	
	public boolean transactionProcces(HttpServletRequest request, Order order, UserService US) {
		// First check the order is linked to a logged user.
		UserDetails UD = (UserDetails) request.getSession().getAttribute("User");
	
		if(UD == null) {
			Notification.show("Not Executing Payment");
			return false;
		}
		
		Optional<User> user = userRepo.findByUserName(UD.getUsername());
		user.ifPresent(u -> order.setUser(u));
		
		Notification.show("Executing Payment");
		//Remove session order and proceed to execute one of the following
		request.getSession().removeAttribute("Order");
		if(utils.randonTransactionSuccess() == false) {
			order.setAccepted(false);
			Notification.show(order.getUser().getUserName());
			order.getUser().setStrikes(order.getUser().getStrikes() + 1);
			US.update(order.getUser());
			Notification.show("Payment Failure");
			this.orderCreate(order);

			return false;
		}else {
			order.setAccepted(true);
			Notification.show(order.getUser().getUserName());
			Notification.show("Payment Success");
			this.orderCreate(order);
			//Create derived schedules
			ACS.orderToSchedules(order);
			
			return true;
		}
		
		
	}
}
