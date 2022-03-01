package com.example.application.data.Service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "services")
@DiscriminatorValue("FINT")
public class FixedInternetService extends InternetService{

	public FixedInternetService(ServiceType serviceType, int gigabytesIncluded, float extraGigabytes) {
		super(serviceType, gigabytesIncluded, extraGigabytes);
		// TODO Auto-generated constructor stub
	}

	public FixedInternetService() {}

}
