package com.example.demo.config;

import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    public void handleMessage(String message){
        System.out.println("Received <" + message + ">");

    }
}
