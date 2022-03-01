package com.example.application;

import com.vaadin.flow.component.dependency.NpmPackage;

import javax.servlet.http.HttpSession;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.vaadin.artur.helpers.LaunchUtil;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements CommandLineRunner {
	
	private JdbcTemplate jdbcTemplate;
	private static boolean orderAcceptance;
    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	//Accepts one order for each two submitted
	public static Boolean orderAccepted() {
		orderAcceptance = !orderAcceptance;
		return orderAcceptance;
	}

    

}
