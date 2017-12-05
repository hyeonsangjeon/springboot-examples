package com.metrics.repository;

import org.springframework.data.repository.CrudRepository;
import com.metrics.dto.Book;


public interface BookRepository extends CrudRepository<Book, Long> {
}