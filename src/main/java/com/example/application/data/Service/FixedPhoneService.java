/**
 * 
 */
package com.example.application.data.Service;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author jaime
 *
 */
@Entity
@Table(name = "services")
@DiscriminatorValue("FPHONE")
public class FixedPhoneService extends PhoneService {

	public FixedPhoneService(ServiceType serviceType) {
		super(serviceType);
		// TODO Auto-generated constructor stub
	}

	public FixedPhoneService() {}
	
}
