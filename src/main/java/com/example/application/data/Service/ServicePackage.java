package com.example.application.data.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
@Entity
@Table(name = "servicePackages")
public class ServicePackage{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long packageID;
	@Column(nullable = false, length =36)
	String packageName;
	@Column(nullable = false, length =25)
	float twelveMonths;
	@Column(nullable = false, length =25)
	float twentyFourMonths;
	@Column(nullable = false, length =25)
	float thirtySixMonths;
	
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "ServicePackage_Service",
    joinColumns = @JoinColumn(name = "pack_id"),
    inverseJoinColumns = @JoinColumn(name = "service_id"))
	private Set<MyService> serviceSet = new HashSet<MyService>();
	
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "ServicePackage_OptProd",
    joinColumns = @JoinColumn(name = "pack_id"),
    inverseJoinColumns = @JoinColumn(name = "optProd_id"))
    private Set<OptionalProduct> optionalProducts = new HashSet<OptionalProduct>();
	
	
	public ServicePackage(String packageName, float twelve, float twentyFour, float thirtySix) {
		this.packageName = packageName;
		this.twelveMonths = twelve;
		this.twentyFourMonths = twentyFour;
		this.thirtySixMonths = thirtySix;
	}
	
	public ServicePackage() {}
	
	
	
	public long getPackageID() {
		return packageID;
	}

	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public float getTwelveMonths() {
		return twelveMonths;
	}
	public void setTwelveMonths(float twelveMonths) {
		this.twelveMonths = twelveMonths;
	}
	public float getTwentyFourMonths() {
		return twentyFourMonths;
	}
	public void setTwentyFourMonths(float twentyFourMonths) {
		this.twentyFourMonths = twentyFourMonths;
	}
	public float getThirtySixMonths() {
		return thirtySixMonths;
	}
	public void setThirtySixMonths(float thirtySixMonths) {
		this.thirtySixMonths = thirtySixMonths;
	}
	public Set<MyService> getServiceSet() {
		return serviceSet;
	}
	public void setServiceSet(Set<MyService> serviceSet) {
		this.serviceSet = serviceSet;
	}

	public Set<OptionalProduct> getOptionalProducts() {
		return optionalProducts;
	}

	public void setOptionalProducts(Set<OptionalProduct> optionalProducts) {
		this.optionalProducts = optionalProducts;
	}

	
	
	
}
