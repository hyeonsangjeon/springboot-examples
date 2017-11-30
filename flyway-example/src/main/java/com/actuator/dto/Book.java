package com.actuator.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Entity
public class Book {
	private static final long serialVersionUID = 8203836758273948712L;  
	
	@Id
	@GeneratedValue
	private Long id ;	
	private String name = "";
	private String author = "";
    private int pages;
        
}
