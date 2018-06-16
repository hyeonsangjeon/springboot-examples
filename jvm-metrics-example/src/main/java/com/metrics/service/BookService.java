package com.metrics.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.metrics.dto.Book;


@Service
public interface BookService {

    public List<Book> getAll();

    public Book save(Book book);

    public Book get(Long bookId);

    public Book update(Book book);

    public int delete(long bookId);

}
