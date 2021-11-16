package com.example;

import com.example.models.Game;
import com.example.models.Player;
import com.example.models.strategy.StrategyType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/jeu")
public class HttpRequestGame {

    private final Player player1 =new Player("nico",null);
    private final Player player2 = new Player("alexis",null);
    private final Game g = new Game(player1,player2,10);

    @GetMapping("/waitPlayerPlay/id={id}")
    synchronized ResponseEntity<Game> waitPalyerPlay(@PathVariable(name = "id")int id) throws InterruptedException {
       // Game g = RestServer.games.get(id);
        //while(!g.canEndTurn()) wait();
        //TODO
        return ResponseEntity.ok(g);
    }

    @PostMapping("/play?gamer={id}&id={id}&move={jeu}")
    synchronized String testJeu(@PathVariable(name = "id")int id,@PathVariable(name = "gamer")int gamer,@PathVariable(name = "move") byte move){
        //TODO savoir sur quel partie on joue
        Game g = RestServer.games.get(id);
        g.humanTakeTurn(g.getPlayerWithId(gamer),move);
        if(g.canEndTurn())notifyAll();
        return "id :"+id+" jeu :";
    }

    @GetMapping("/allStrategy")
    public Object[] allStrat(){
        return Arrays.stream(StrategyType.values()).map((value)->value.getName()).toArray();
    }
}
