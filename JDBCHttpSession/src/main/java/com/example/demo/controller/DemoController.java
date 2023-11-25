package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Message;

@RestController
public class DemoController {

    private int count = 0;
    private String message;

    @GetMapping("/")
    public Message index(@RequestParam(value = "word", defaultValue = "Hello World!!") String word) {

        count ++;
        message = word;

        Message msg = new Message(count, message);

        return msg;
    }
}
