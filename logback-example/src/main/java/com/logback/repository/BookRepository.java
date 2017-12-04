package com.logback.repository;

import org.springframework.data.repository.CrudRepository;
import com.logback.dto.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
}