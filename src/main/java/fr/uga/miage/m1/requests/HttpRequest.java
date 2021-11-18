package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.models.Game;
import fr.uga.miage.m1.RestServer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/")
public class HttpRequest {

    //ici pour recup toutes les parties creer et non rempli
    @GetMapping("allGames")//ok
    public ResponseEntity<List<Game>> allGames(){
        return ResponseEntity.ok(new ArrayList<>(RestServer.getGamesCollection()));
    }

    //ici permet de creer une partie
    @PostMapping("createGame/maxTurnCount={maxTurnCount}")
    public ResponseEntity<Game> gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount){//ok
        Game g = new Game(null, null, maxTurnCount);
        RestServer.registerGame(g);
        return ResponseEntity.ok(g);
    }

}