package com.example.application.data.Service;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "services")
@DiscriminatorValue("MPHONE")
public class MobilePhoneService extends PhoneService {

	@Column(nullable = true, length =25)
	int minutesIncluded;
	@Column(nullable = true, length =25)
	int smsIncluded;
	@Column(nullable = true, length =25)
	float extraMinuteFee;
	@Column(nullable = true, length =25)
	float extraSmsFee;
	
	public MobilePhoneService(ServiceType serviceType, int minutesIncluded, int smsIncluded, float extraMinuteFee, float extraSmsFee) {
		super(serviceType);
		// TODO Auto-generated constructor stub
		this.minutesIncluded = minutesIncluded;
		this.smsIncluded = smsIncluded;
		this.extraMinuteFee = extraMinuteFee;
		this.extraSmsFee = extraSmsFee;
	}
	
	public MobilePhoneService() {}
	
	public int getMinutesIncluded() {
		return minutesIncluded;
	}
	public void setMinutesIncluded(int minutesIncluded) {
		this.minutesIncluded = minutesIncluded;
	}
	public int getSmsIncluded() {
		return smsIncluded;
	}
	public void setSmsIncluded(int smsIncluded) {
		this.smsIncluded = smsIncluded;
	}
	public float getExtraMinuteFee() {
		return extraMinuteFee;
	}
	public void setExtraMinuteFee(float extraMinuteFee) {
		this.extraMinuteFee = extraMinuteFee;
	}
	public float getExtraSmsFee() {
		return extraSmsFee;
	}
	public void setExtraSmsFee(float extraSmsFee) {
		this.extraSmsFee = extraSmsFee;
	}
	
	
	
}
