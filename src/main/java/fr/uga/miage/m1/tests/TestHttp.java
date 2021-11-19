package fr.uga.miage.m1.tests;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestHttp {

    @GetMapping("/ping")
    public String pong() {
      return "pong";
    }

    @GetMapping("/michelle")
    public String test(){
        return "aah";
    }
}
