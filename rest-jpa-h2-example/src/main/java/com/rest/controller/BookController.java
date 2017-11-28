package com.rest.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.rest.dto.Book;
import com.rest.service.BookService;



@RestController
@RequestMapping("/book")
public class BookController {


  @Autowired
  private BookService bookService; 
  
  
  //Create
  @RequestMapping(value="/add", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> createBook(@RequestBody @Valid Book book){	  
	  Book res = bookService.save(book);	  
	  Map<String, Object> response = new LinkedHashMap<String, Object>();
	  response.put("message", "Book created OK");
	  response.put("book", res);
	  return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
  
  
  //Read All
  @RequestMapping(method = RequestMethod.GET)
  public List<Book> getAll(){
      List<Book> books = bookService.getAll();	  
	  return books;
  }
  
  
  //Read One
  @RequestMapping(value="/{bookId}", method=RequestMethod.GET)
  public Book get(Long bookId){	
	Book book = bookService.get(bookId);	    
	return book;
  }
  
  
  //Update
  @RequestMapping(value="/{bookId}", method = RequestMethod.PUT)
  public ResponseEntity<Map<String, Object>> updateBook(@RequestBody @Valid Book book, BindingResult result){
	  
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
  
  
 


}
