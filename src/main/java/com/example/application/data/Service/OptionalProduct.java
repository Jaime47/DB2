package com.example.application.data.Service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.example.application.data.Order;

@Entity
@Table(name = "optionalProducts")
public class OptionalProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@Column(nullable = false, length =25)
	long price;
	@Column(nullable = false, length =25)
	String name;
	
    @ManyToMany(mappedBy = "optionalProducts")
    private Set<ServicePackage> servicePackage = new HashSet<ServicePackage>();
    
    @ManyToMany(mappedBy = "optionalProducts")
    private Set<Order> order = new HashSet<Order>();
    
    public OptionalProduct() {}
	
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
