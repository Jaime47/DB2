package com.example.application.data.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.application.data.repository.EmployeeRepository;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true, length =45)
    private String name;
	@Column(nullable = false, length =45)
    private String password;
	
	
	
	public Employee (String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public Employee () {}
	
	
	static public void EmployeeLogIn (HttpServletRequest request, Employee employee, EmployeeRepository ER) {
		try {
		Optional<Employee> emp = ER.findByNameAndPassword(employee.getName(), employee.getPassword());
		if (emp == null) {
			throw new UsernameNotFoundException("Incorrect name or password");
		}
		emp.ifPresent(e ->request.getSession().setAttribute("User", e));
		}
		catch(Exception e) {
			throw new UsernameNotFoundException("Incorrect name or password");
		}

	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
