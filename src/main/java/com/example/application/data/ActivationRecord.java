package com.example.application.data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.application.ServiceProductEnum;
import com.example.application.data.entity.User;
@Entity
@Table(name = "Schedule")
public class ActivationRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length =45)
	private long user;
	@Column(nullable = false, length =45)
	private LocalDate startDate;
	@Column(nullable = false, length =45)
	private LocalDate endDate;
	@Column(nullable = false, length =45)
	private ServiceProductEnum productType;
	@Column(nullable = false, length =45)
	private long elementId;
	
	
	public ActivationRecord(long user, LocalDate startDate2, LocalDate endDate2, ServiceProductEnum type, long elemId){
		this.user = user;
		this.startDate = startDate2;
		this.endDate = endDate2;
		this.productType = type;
		this.elementId = elemId;
	
	}


	public long getUser() {
		return user;
	}


	public void setUser(long user) {
		this.user = user;
	}


	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	public ServiceProductEnum getProductType() {
		return productType;
	}


	public void setProductType(ServiceProductEnum productType) {
		this.productType = productType;
	}


	public long getElementId() {
		return elementId;
	}


	public void setElementId(long elementId) {
		this.elementId = elementId;
	}
	
}
