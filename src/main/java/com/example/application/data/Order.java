package com.example.application.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.application.ActivationRecordService;
import com.example.application.OrderService;
import com.example.application.ServiceProductEnum;
import com.example.application.data.Service.MyService;
import com.example.application.data.Service.OptionalProduct;
import com.example.application.data.Service.ServicePackage;
import com.example.application.data.entity.User;
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length =45)
	private LocalDate date;
	@Column(nullable = false, length =45)
	private int timePurchased;
    @OneToOne
	private User user;
    @OneToOne
	private ServicePackage servPack;
    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "Order_OptProd",
    joinColumns = @JoinColumn(name = "order_id"),
    inverseJoinColumns = @JoinColumn(name = "optProd_id"))
	private Set<OptionalProduct> optionalProducts = new HashSet<OptionalProduct>();
	@Column(nullable = false, length =45)
	private float price;
	@Column(nullable = false, length =45)
	private Boolean accepted;
	@Column(nullable = false, length =45)
	private LocalDate startDate;
	
	public Order (LocalDate date, int timePurchased, User user, ServicePackage servPack, Set<OptionalProduct> optProducts, LocalDate startDate) {
		this.date = date;
		this.timePurchased = timePurchased;
		this.user = user;
		this.servPack = servPack;
		this.optionalProducts.addAll(optProducts);
		this.accepted=false;
		this.price = 0;
		this.startDate = startDate;
	}
	
	
	public Order (){
		this.date = LocalDate.now();
		this.timePurchased = 0;
		this.user = null;
		this.servPack = null;
		this.accepted=false;
		this.price = 0;
		this.startDate = null;
	}
	
	
	@SuppressWarnings("unused")
	private void mensualPriceCalculator(OrderService OS) {
		OS.calculatePrice(this);
	}
	
	
	@SuppressWarnings("unused")
	private void orderToRecords(ActivationRecordService ARS){
		
		LocalDate endDate = this.startDate.plusMonths(timePurchased);
		
		Set<OptionalProduct> completeOptionalProducts = new HashSet<OptionalProduct>();
		
		completeOptionalProducts.addAll(this.getOptionalProducts());
		
		completeOptionalProducts.addAll(this.getServPack().getOptionalProducts());
		
		Iterator<OptionalProduct> iter = completeOptionalProducts.iterator();
		
		while(iter.hasNext()) {
			OptionalProduct opt = iter.next();
			ActivationRecord ar = new ActivationRecord(this.user.getId(),this.startDate , endDate, ServiceProductEnum.PRODUCT, opt.getId());
			ARS.activationRecordCreate(ar);
		}
		
		@SuppressWarnings("unchecked")
		Iterator<MyService> iter1 = (Iterator<MyService>) this.servPack.getServiceSet();
		
		while(iter1.hasNext()) {
			MyService serv = iter1.next();
			ActivationRecord ar = new ActivationRecord(this.user.getId(),this.startDate , endDate, ServiceProductEnum.SERVICE, serv.getId());
			ARS.activationRecordCreate(ar);
		}
	}

	
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getTimePurchased() {
		return timePurchased;
	}

	public void setTimePurchased(int timePurchased) {
		this.timePurchased = timePurchased;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ServicePackage getServPack() {
		return servPack;
	}

	public void setServPack(ServicePackage servPack) {
		this.servPack = servPack;
	}

	public Set<OptionalProduct> getOptionalProducts() {
		return optionalProducts;
	}

	public void setOptionalProducts(Set<OptionalProduct> optionalProducts) {
		this.optionalProducts = optionalProducts;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float Price) {
		this.price = Price;
	}

	public Boolean getAccepted() {
		return accepted;
	}

	public void setAccepted(Boolean accepted) {
		this.accepted = accepted;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate e) {
		this.startDate = e;
	}
	
	
}


