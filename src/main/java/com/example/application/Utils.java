package com.example.application;


import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.application.data.Order;
@Service
public class Utils {
	
	Random random = new Random();

   public boolean randonTransactionSuccess() {
	   return random.nextBoolean();
   }
   
}