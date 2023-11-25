package com.example.demo.entity;

public class Message {
    private int num;
    private String message;

    public Message (){}

    public Message(int num, String message) {
        this.num = num;
        this.message = message;
    }

    public int getNum() {
        return num;
    }

    public String getMessage() {
        return message;
    }
}
