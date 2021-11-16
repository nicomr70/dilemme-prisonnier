package com.example;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.models.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
    public static Map<Integer, Game> games = new HashMap<>();

    public static void main(String[] args){
        System.out.println("go Firebase");
        SpringApplication.run(RestServer.class, args);
    }

}
