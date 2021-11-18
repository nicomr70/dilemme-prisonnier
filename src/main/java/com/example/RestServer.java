package com.example;
import java.util.HashMap;
import java.util.Map;

import com.example.models.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
    public static Map<Integer, Game> games = new HashMap<>();

    public static void main(String[] args){
        SpringApplication.run(RestServer.class, args);
    }

}
