package com.example.application;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.application.data.Service.OptionalProduct;
import com.vaadin.flow.component.notification.Notification;
@Service
public class SessionService {

	
    public UserDetails logIn(HttpServletRequest request, MyUserDetailsService userDetails, String name, String password) {
        
    	try {
    		HttpSession session=request.getSession();
            UserDetails UD = userDetails.loadUserByUsernameandPassword(name, password);
            session.setAttribute("User", UD);
            
            return UD;
        	}
        	catch(Exception e1) {
        		throw new UsernameNotFoundException("Incorrect name or password");
        	} 
    }
}
