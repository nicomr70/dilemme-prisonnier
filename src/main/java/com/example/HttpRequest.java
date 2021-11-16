package com.example;

import com.example.models.Game;
import com.example.models.player.Player;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.RestServer.games;


@CrossOrigin
@RestController
@RequestMapping("/")
public class HttpRequest {

    //ici pour recup toutes les parties creer et non rempli
    @GetMapping("allGames")//ok
    public ResponseEntity<Object[]> allGames(){
        return ResponseEntity.ok(games.values().toArray());
    }

    //ici permet de creer une partie
    @PostMapping("createGame/maxTurnCount={maxTurnCount}")
    public ResponseEntity<Game> gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount){//ok
        Game g = new Game(null, null, maxTurnCount);
        games.put(g.getId(),g);
        return ResponseEntity.ok(g);
    }

    @PostMapping("joinGame/gameId={gameId}&playerName={playerName}")
    synchronized public ResponseEntity<Game> joinGame(@PathVariable("gameId")int gameId, @PathVariable("playerName")String playerName) {
        Player p = new Player(playerName, null);
        Game currentGame = games.get(gameId);
        currentGame.setPlayer(p);
        if (currentGame.areAllPlayersHere())notifyAll();
        return ResponseEntity.ok(currentGame);
    }


    @GetMapping("waitLastPlayer/id={id}")
    synchronized public ResponseEntity<Game> waitLastPlayer(@PathVariable(name = "id")int id) throws InterruptedException {
        Game g = games.get(id);
        while(!(g.areAllPlayersHere()))wait();
        return ResponseEntity.ok(g);
    }

    @GetMapping("gameInitialState/id={id}")
    public ResponseEntity<Game> gameInitialState(@PathVariable(name = "id")int id){
        return ResponseEntity.ok(games.get(id));
    }

}