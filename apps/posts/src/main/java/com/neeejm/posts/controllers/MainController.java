package com.neeejm.posts.controllers;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(
    path = "",
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class MainController {

    @GetMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok().body(
            Map.of("msg", "hello test") 
        );
    }
}
