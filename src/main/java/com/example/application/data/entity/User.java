package com.example.application.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "users")
public class User {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true, length =45)
    private String userName;
	@Column(nullable = false, length =45)
    private String email;
	@Column(nullable = false, length =45)
    private String password;
	@Column(nullable = false, length = 45)
	private int strikes;
    
    
    
  


	public User(String username, String email, String password) {
    	this.email = email;
    	this.password = password;
    	this.userName = username;
    	this.strikes = 0;
    	

    }
    
    
    public User() {
		// TODO Auto-generated constructor stub
	}


    
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
 
    public void incStrikes() {
    	this.strikes += 1;
    }

	public int getStrikes() {
		return strikes;
	}


	public void setStrikes(int strikes) {
		this.strikes = strikes;
	}

}
