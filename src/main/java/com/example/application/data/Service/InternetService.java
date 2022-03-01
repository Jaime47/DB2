package com.example.application.data.Service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "services")
public abstract class InternetService extends MyService{

	@Column(nullable = true, length =25)
	int gigabytesIncluded;
	@Column(nullable = true, length =25)
	float extraGigabyteFee;
	
	public InternetService(ServiceType serviceType, int gigabytesIncluded, float extraGigabytes) {
		super(serviceType);
		// TODO Auto-generated constructor stub
		this.gigabytesIncluded = gigabytesIncluded;
		this.extraGigabyteFee = extraGigabytes;
	}
	
	public InternetService() {}
	
	public int getGigabytesIncluded() {
		return gigabytesIncluded;
	}
	public void setGigabytesIncluded(int gigabytesIncluded) {
		this.gigabytesIncluded = gigabytesIncluded;
	}
	public float getExtraGigabyteFee() {
		return extraGigabyteFee;
	}
	public void setExtraGigabyteFee(float extraGigabyteFee) {
		this.extraGigabyteFee = extraGigabyteFee;
	}
}
