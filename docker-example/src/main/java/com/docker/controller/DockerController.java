package com.docker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DockerController {
    private static final Logger logger = LoggerFactory.getLogger(DockerController.class);

    //Index
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> index() {
        logger.debug("HttpRequestServlet Route");
        return new ResponseEntity<>("Welcome Springboot docker running", HttpStatus.OK);
    }

}
