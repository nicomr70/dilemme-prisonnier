package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.models.game.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;

import static fr.uga.miage.m1.models.game.Game.updateAllGames;


@CrossOrigin
@RestController
@RequestMapping("/")
public class HttpRequestBase {


    //ici pour recup toutes les parties creer et non rempli
    @GetMapping("allGames")//ok
    public SseEmitter allGames(){
        return Game.poolAllGames.newEmitter("AllGames");
    }

    //ici permet de creer une partie
    @PutMapping("createGame/maxTurnCount={maxTurnCount}")
    public ResponseEntity<Boolean> gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount) {
        Game g = new Game(maxTurnCount);
        Game.gamePool.registerGame(g);
        updateAllGames();
        return ResponseEntity.ok(true);
    }

    @GetMapping("initialState")
    public ResponseEntity<Collection<Game>> send(){
        return ResponseEntity.ok(Game.gamePool.asCollection());
    }






}