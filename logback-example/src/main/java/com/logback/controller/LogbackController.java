package com.logback.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.logback.dto.Book;
import com.logback.repository.BookRepository;


@RestController
public class LogbackController {
    private static final Logger logger = LoggerFactory.getLogger(LogbackController.class);

    @Autowired
    BookRepository bookRepository;


    //Index
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Book> getAll() {
        Iterable<Book> all = bookRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false).collect(toList());
    }


    //@Bean CommandLineRunner means that it will run after the springboot autoconfiguration.
    @Bean
    CommandLineRunner findAll(BookRepository bookRepo) {
        return args -> {
            bookRepo.findAll().forEach(book ->
                    logger.debug("[MIGRATION DB INFO] : " + book.toString())
            );
        };

    }
}
