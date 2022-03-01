/**
 * 
 */
package com.example.application.data.Service;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jaime
 *
 */
@Entity
@Table(name = "services")
public abstract class PhoneService extends MyService {

	public PhoneService(ServiceType serviceType) {
		super(serviceType);
		// TODO Auto-generated constructor stub
	}

	
	public PhoneService() {}
}
