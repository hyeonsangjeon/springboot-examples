package com.actuator.repository;

import org.springframework.data.repository.CrudRepository;
import com.actuator.dto.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
}