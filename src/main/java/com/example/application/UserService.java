package com.example.application;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
@Service
public class UserService extends HttpServlet {
	
	
	@Autowired
	private UserRepository repo;
	@Autowired
	private EntityManager em;

	
	/*Management layer function: Creates a new user into the system*/
	public User createUser (User beanUser) {
		User user = beanUser;
		repo.save(user);
		return user;
	}
	
	
	public void update (User user) {
		repo.save(user);
	}
	
	
	public User createUser (String username, String email, String password) {
		User user = new User();
		user.setUserName(username);
		user.setEmail(email);
		user.setPassword(password);
		repo.save(user);
		return user;
	}
	

    public User findUser(int id) {
    	return em.find(User.class, id);
    }
    
    
    public User checkCredentials(String username, String password) {
		User u = (User) em.createQuery("SELECT u FROM user u" + "WHERE u.username = ?1" + "AND u.password = ?2", User.class);
		return u;
	}
    
    
    public void removeUser(int id) {
    	User user = findUser(id);
    	if (user != null) {
    		em.remove(user);
    	}
    }
}
