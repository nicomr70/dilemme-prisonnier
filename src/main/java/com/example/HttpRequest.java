package com.example;

import com.example.models.Game;
import com.example.models.Player;
import com.example.models.strategy.StrategyType;
import jdk.jfr.internal.consumer.StringParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String gameFactory(@PathVariable(name = "maxTurnCount") int maxTurnCount){//ok
        Game g = new Game(null, null, maxTurnCount);
        games.put(g.getId(),g);
        return g.renvoiejeu().toString();
    }

    @PostMapping("joinGame/gameId={gameId}&playerName={playerName}")
    synchronized public String joinGame(@PathVariable("gameId")int gameId, @PathVariable("playerName")String playerName) {
        Player p = new Player(playerName, null);
        Game currentGame = games.get(gameId);
        currentGame.setPlayer(p);
        if (currentGame.areAllPlayersHere())notifyAll();
        return currentGame.renvoiejeu().toString();
    }


    @GetMapping("waitLastPlayer/id={id}")
    synchronized public String waitLastPlayer(@PathVariable(name = "id")int id) throws InterruptedException {
        Game g = games.get(id);
        while(!(g.areAllPlayersHere()))wait();
        return g.renvoiejeu().toString();
    }

    @GetMapping("gameInitialState/id={id}")
    public String gameInitialState(@PathVariable(name = "id")int id){
        return games.get(id).renvoiejeu().toString();
    }

}