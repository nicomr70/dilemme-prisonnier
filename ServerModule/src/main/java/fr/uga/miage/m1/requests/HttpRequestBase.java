package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.interfaces.GameServiceI;
import fr.uga.miage.m1.models.game.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;


@CrossOrigin
@RestController
@RequestMapping("/")
public class HttpRequestBase {
    @Autowired
    GameServiceI gameService;

    //ici pour recup toutes les parties creer et non rempli
    @GetMapping("allGames")//ok
    public SseEmitter allGames(){
        return gameService.newEmitterAllGames();
    }

    //ici permet de creer une partie
    @PutMapping("createGame/maxTurnCount={maxTurnCount}")
    public ResponseEntity<Boolean> gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount) throws GameException {
        gameService.createGame(maxTurnCount);
        return ResponseEntity.ok(true);
    }

    @GetMapping("initialState")
    public ResponseEntity<Collection<Game>> send(){
        return ResponseEntity.ok(gameService.getAllGame());
    }






}