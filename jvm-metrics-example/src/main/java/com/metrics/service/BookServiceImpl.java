package com.metrics.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.metrics.dto.Book;
import com.metrics.repository.BookRepository;

import static java.util.stream.Collectors.*;


@Service
@Transactional
@EnableAutoConfiguration
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;


    @Override
    public List<Book> getAll() {
        Iterable<Book> all = bookRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false).collect(toList());
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book get(Long bookId) {
        Book book = bookRepository.findOne(bookId);
        return book;
    }

    @Override
    public Book update(Book book) {
        Book updBook = bookRepository.findOne(book.getId());
        updBook.setName(book.getName());
        bookRepository.save(updBook);
        return updBook;
    }

    @Override
    public int delete(long bookId) {

        bookRepository.delete(bookId);

        return 1;
    }

}
