package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public ResponseEntity<String> generate500(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    public ResponseEntity<String> getDemo(){
        return ResponseEntity.ok("demo response");
    }
}
