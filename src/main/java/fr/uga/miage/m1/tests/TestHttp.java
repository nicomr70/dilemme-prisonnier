package fr.uga.miage.m1.tests;

import java.util.ArrayList;
import java.util.Map;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class TestHttp {

    @GetMapping("/ping")
    String pong() {
      return "pong";
    }

    @GetMapping("/michelle")
    String test(){
        return "aah";
    }
}
