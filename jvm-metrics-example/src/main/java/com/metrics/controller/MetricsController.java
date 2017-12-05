package com.metrics.controller;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;

import com.sun.management.OperatingSystemMXBean;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.metrics.dto.Book;
import com.metrics.repository.BookRepository;
import com.metrics.service.BookService;



@RestController
public class MetricsController {
  private static final Logger logger = LoggerFactory.getLogger(MetricsController.class);
  
  private static final double threshold = 30;
  
  //This is Counter Interface used request count sum in this example
  @Autowired
  CounterService counter;
  
  //This is Gauge Interface  measuring jvm resource concrete class  working job
  @Autowired
  GaugeService gauge;
  
  @Autowired
  private BookService bookService; 
  
  @SuppressWarnings("restriction")
  
  private OperatingSystemMXBean systemMBean;
    
  //Create
  @RequestMapping(value="/add", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> createBook(@RequestBody @Valid Book book){	  
	  counter.increment("rest.create.invoked.index");
		  
	  Book res = bookService.save(book);	  
	  Map<String, Object> response = new LinkedHashMap<String, Object>();
	  response.put("message", "Book created OK");
	  response.put("book", res);
	  	 
	  return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
  
  
  //Read All
  @SuppressWarnings("restriction")
  @RequestMapping(method = RequestMethod.GET)
  public List<Book> getAll() throws InterruptedException{
	  counter.increment("rest.readAll.invoked.index");        						//Adding this count check to Spring Metrics[1]
	  
	  systemMBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);	  	  	  	  		  	  	  	 	  
	  long nanoBefore = System.nanoTime();   											
	  long cpuBefore = systemMBean.getProcessCpuTime(); 								// CPU time used by the process on which the Java virtual machine is running in nanoseconds.	  
	  
	  //------------------------------------------------------------------------------------------------|
	  //some loaded job,
      List<Book> books = bookService.getAll();
      
      // and Let's assume a synchronization operation. It just await processing without using cpu. change this value;
	  Thread.sleep(500);
	  //------------------------------------------------------------------------------------------------|
	  
      long cpuAfter = systemMBean.getProcessCpuTime();
      long nanoAfter = System.nanoTime();
            
      long momentRatio;                      											//Percentage of moment CPU Usage Time per a Job processing time
      if (nanoAfter > nanoBefore)
    	  momentRatio = ((cpuAfter-cpuBefore)*100L)   /   (nanoAfter-nanoBefore);
      else momentRatio = 0;
              
      double spentCpuOS = systemMBean.getSystemCpuLoad()*100L;                         //Returns the "recent cpu usage" for the whole system.
      double spentCpuJVM = systemMBean.getProcessCpuLoad()*100L;                       //Returns the "recent cpu usage" for the Java Virtual Machine process. 
      double deltaMs = (nanoAfter - nanoBefore)*100000L;                               //Job Processing time (Milliseconds)
      double pMemStat = systemMBean.getTotalPhysicalMemorySize() - systemMBean.getFreePhysicalMemorySize(); // present OS Available Memory status.
      
      
      
      if(threshold > momentRatio) {                                                    //Adding some specify value like gauging double value
    	  	  String timer = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a").format(new Date(System.currentTimeMillis()));
    	      gauge.submit("gauge.rest.warnning.getAll.spentCpuOS ["+timer+"] : ", spentCpuOS );
          gauge.submit("gauge.rest.warnning.getAll.spentCpuJVM ["+timer+"] : ", spentCpuJVM);
          gauge.submit("gauge.rest.warnning.getAll.momentRatio ["+timer+"] : ", momentRatio);
          gauge.submit("gauge.rest.warnning.getAll.jobprocesstime ["+timer+"] : ", deltaMs);
          gauge.submit("gauge.rest.warnning.getAll.memoryStatus ["+timer+"] : ", pMemStat);
      }
      
      
      logger.debug("recent cpu usage in OS: "+spentCpuOS+"%");  
      logger.debug("recent cpu usage in JVM: "+spentCpuJVM+"%");        
      logger.debug("moment differential cpu using Ratio :"+momentRatio+"%");
	  return books;
  }
  
  
  //Read One
  @RequestMapping(value="/{bookId}", method=RequestMethod.GET)
  public Book get(Long bookId){	
	counter.increment("rest.readone.invoked.index");
	
	Book book = bookService.get(bookId);	    
	return book;
  }
  
  
  //Update
  @RequestMapping(value="/{bookId}", method = RequestMethod.PUT)
  public ResponseEntity<Map<String, Object>> updateBook(@RequestBody @Valid Book book, BindingResult result){
	  counter.increment("rest.update.invoked.index");
	  
	  if (result.hasErrors()) {
          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
  
	  Book upd = bookService.update(book);
	  
	  Map<String, Object> response = new LinkedHashMap<String, Object>();
	  response.put("message", "Book update OK");
	  response.put("book", upd);	  
	  return new ResponseEntity<>(response, HttpStatus.OK);
	  
  }
  
  
  //Delete
  @RequestMapping(value="/{bookId}", method = RequestMethod.DELETE)
  public ResponseEntity<Map<String, Object>> deleteBook(@RequestBody @Valid Book book) throws Exception{	  
	  counter.increment("rest.delete.invoked.index");
	  
	  int del = 0;	  
	  del = bookService.delete(book.getId());	  
	  Map<String, Object> response = new LinkedHashMap<String, Object>();
	  
	  if(del==1) {
		  response.put("message", "Book delete OK");
	  }else {
		  throw new Exception(); 	  
	  }	  	  	  
	  return new ResponseEntity<>(response, HttpStatus.OK);
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
