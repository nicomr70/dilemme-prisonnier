package com.example;

import com.example.models.Game;
import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;
import com.example.models.strategy.StrategyType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;

import static com.example.RestServer.games;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class HttpRequestGame {

    SseEmitter emitter = new SseEmitter();

    @GetMapping("/waitPlayerPlay/gameId={gameId}")
    synchronized ResponseEntity<Game> waitPlayerPlay(@PathVariable(name = "gameId")int gameId) throws InterruptedException {
        return ResponseEntity.ok(games.get(gameId).waitPlayerPLay());
    }

    @PostMapping("/play/gameId={gamIid}/playerId={playerId}/move={move}")
    synchronized ResponseEntity<Game> playMove(@PathVariable(name = "gameId")int gameId, @PathVariable(name = "playerId")int playerId, @PathVariable(name = "move") PlayerChoice move){
        //TODO savoir sur quel partie on joue (id)
        return ResponseEntity.ok(games.get(gameId).playMove(playerId,move));
    }

    @GetMapping("/allStrategy")
    public ResponseEntity<Object[]> allStrategy(){
        return ResponseEntity.ok(Arrays.stream(StrategyType.values()).map((value)->value.getName()).toArray());
    }

    @GetMapping("/allMove")
    public ResponseEntity<PlayerChoice[]> allMove(){
        return ResponseEntity.ok(PlayerChoice.values());
    }

    //ok
    @GetMapping("waitLastPlayer/gameId={gameId}")
    public ResponseEntity<Game> waitLastPlayer(@PathVariable(name = "gameId")int gameId) throws InterruptedException {
        return ResponseEntity.ok(games.get(gameId).waitSecondPlayer());
    }
    //ok
    @GetMapping("initialState/gameId={gameId}")
    public ResponseEntity<Game> gameInitialState(@PathVariable(name = "gameId")int gameId){
        return ResponseEntity.ok(games.get(gameId));
    }

    //ok
    @PostMapping("join/gameId={gameId}/playerName={playerName}")
    public ResponseEntity<Player> joinGame(@PathVariable("gameId")int gameId, @PathVariable("playerName")String playerName) {
        return ResponseEntity.ok(games.get(gameId).addPlayer(playerName));
    }

    @GetMapping("/stream-test/id={gameId}")
    public SseEmitter streamTest(@PathVariable(name = "gameId")int gameId) throws IOException {
        Thread t = new Thread(()->{
            try {
                emitter.send(games.get(gameId));
                emitter.complete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return emitter;
    }
}
