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

    @GetMapping("/waitPlayerPlay/id={id}")
    synchronized ResponseEntity<Game> waitPlayerPlay(@PathVariable(name = "id")int id) throws InterruptedException {
        Game g = RestServer.games.get(id);
        while(!g.canEndTurn()) wait();
        return ResponseEntity.ok(g);
    }

    @PostMapping("/play?gamer={id}&id={id}&move={jeu}")
    synchronized ResponseEntity<Game> playMove(@PathVariable(name = "id")int id, @PathVariable(name = "gamer")int gamer, @PathVariable(name = "move") byte move){
        //TODO savoir sur quel partie on joue
        Game g = RestServer.games.get(id);
        g.humanTakeTurn(g.getPlayerWithId(gamer),move);
        if(g.canEndTurn())notifyAll();
        return ResponseEntity.ok(g);
    }

    @GetMapping("/allStrategy")
    public ResponseEntity<Object[]> allStrat(){
        return ResponseEntity.ok(Arrays.stream(StrategyType.values()).map((value)->value.getName()).toArray());
    }
}
