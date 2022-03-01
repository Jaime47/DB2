package com.example.application.data.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.Order;
import com.example.application.data.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long>{
	Collection<Order> findByAcceptedAndUser_id(boolean isAccepted, long userId);
}
