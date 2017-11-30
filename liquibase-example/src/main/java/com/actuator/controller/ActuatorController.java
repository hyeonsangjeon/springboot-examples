package com.actuator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.actuator.repository.BookRepository;


@RestController
public class ActuatorController {
  private static final Logger logger = LoggerFactory.getLogger(ActuatorController.class);
  
  //Index
  @RequestMapping(value="/", method = RequestMethod.GET)
  public ResponseEntity<String> index(){	  	  
	  return new ResponseEntity<>("Welcome Springboot liquibase", HttpStatus.OK);
  }
  
  //@Bean CommandLineRunner means that it will run after the springboot autoconfiguration.
  @Bean
  CommandLineRunner findAll(BookRepository bookRepo) {	  
	  return args -> {		  		 
		  bookRepo.findAll().forEach(book ->		  
		      logger.debug("[MIGRATION DB INFO] : "+book.toString())
		  );
	  };
	  
  }
  
  
  

}
