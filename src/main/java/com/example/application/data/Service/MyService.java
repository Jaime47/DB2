/**
 * 
 */
package com.example.application.data.Service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author jaime
 *
 */
@Entity
@Table(name = "services")
@Inheritance()
@DiscriminatorColumn(
    name="discriminator",
    discriminatorType= DiscriminatorType.STRING
)
@DiscriminatorValue(value="S")
public abstract class MyService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length =25)
	private ServiceType serviceType;
	
    @ManyToMany(mappedBy = "serviceSet")
    private Set<ServicePackage> servicePackage = new HashSet<ServicePackage>();
	
	
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	
	
	public MyService (ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	
	public MyService() {}
	
	public long getId() {
		return id;
	}
	
	public Set<ServicePackage> getServicePackage() {
		return servicePackage;
	}
	public void setServicePackage(Set<ServicePackage> servicePackage) {
		this.servicePackage = servicePackage;
	}
	
	
}

