package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.models.Game;
import fr.uga.miage.m1.RestServer;
import fr.uga.miage.m1.models.SseEmitterPool;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collection;


@CrossOrigin
@RestController
@RequestMapping("/")
public class HttpRequest {

    private static final SseEmitterPool poolAllGames = new SseEmitterPool();
    //ici pour recup toutes les parties creer et non rempli
    @GetMapping("allGames")//ok
    public SseEmitter allGames(){
        return poolAllGames.sseEmitterFactory("AllGames");
    }

    //ici permet de creer une partie
    @PutMapping("createGame/maxTurnCount={maxTurnCount}")
    public ResponseEntity<Boolean> gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount) throws IOException {//ok
        Game g = new Game(null, null, maxTurnCount);
        RestServer.getGamePool().registerGame(g);
        updateAllGames();
        return ResponseEntity.ok(true);
    }

    @GetMapping("initialState")
    public ResponseEntity<Collection<Game>> send(){
        return ResponseEntity.ok(RestServer.getGamePool().getGamesCollection());
    }

    public static void updateAllGames() throws IOException {
        poolAllGames.sendAll(RestServer.getGamePool().getGamesCollection());
    }




}