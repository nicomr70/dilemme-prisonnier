package com.example;

import com.example.models.Game;
import com.example.models.Player;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/jeu")
public class HttpRequest {
    private final Player player1 =new Player("nico",null);
    private final Player player2 = new Player("alexis",null);
    private final Game g = new Game(player1,player2);


    @GetMapping("/ping2")
    int ping(){
        return 1;
    }

    @GetMapping("/ping")
    String db() {
        return "pong";
    }

    @GetMapping("/miseEnAttend")
    synchronized String testAsync() throws InterruptedException {
        while(!g.canEndTurn()) wait();
        return g.toString();
    }

    @PostMapping("jeu/id={id}&prop={jeu}")
    synchronized String testJeu(@PathVariable(name = "id")int id,@PathVariable(name = "jeu")String jeu){
        g.humanTakeTurn(id ==1 ? player1 : player2,jeu.equals("c") ? 1 :(byte)0);
        if(g.canEndTurn())notifyAll();
        return "id :"+id+" jeu :"+jeu;
    }
}