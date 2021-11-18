package com.example.requests;

import com.example.models.Game;
import com.example.models.player.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.RestServer.games;


@CrossOrigin
@RestController
@RequestMapping("/")
public class HttpRequest {

    //ici pour recup toutes les parties creer et non rempli
    @GetMapping("allGames")//ok
    public ResponseEntity<List<Game>> allGames(){
        return ResponseEntity.ok(new ArrayList<>(games.values()));
    }

    //ici permet de creer une partie
    @PostMapping("createGame/maxTurnCount={maxTurnCount}")
    public ResponseEntity<Game> gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount){//ok
        Game g = new Game(null, null, maxTurnCount);
        games.put(g.getId(),g);
        return ResponseEntity.ok(g);
    }

}