package com.example.requests;

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
import static java.lang.Thread.sleep;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class HttpRequestGame {
    @GetMapping("/waitPlayerPlay/gameId={gameId}")
    SseEmitter waitPlayerPlay(@PathVariable(name = "gameId")int gameId) {
        return games.get(gameId).getSseEmitter();
    }

    @PostMapping("/play/gameId={gameId}/playerId={playerId}/move={move}")
    synchronized ResponseEntity<Game> playMove(@PathVariable(name = "gameId")int gameId, @PathVariable(name = "playerId")int playerId, @PathVariable(name = "move") PlayerChoice move) throws Exception {
        return ResponseEntity.ok(games.get(gameId).playMove(playerId,move));
    }

    @GetMapping("/allStrategies")
    public ResponseEntity<Object[]> allStrategies(){
        return ResponseEntity.ok(Arrays.stream(StrategyType.values()).map(StrategyType::getName).toArray());
    }

    @GetMapping("/allMoves")
    public ResponseEntity<PlayerChoice[]> allMoves(){
        return ResponseEntity.ok(PlayerChoice.values());
    }

    //ok
    @GetMapping("waitLastPlayer/gameId={gameId}")
    public SseEmitter waitLastPlayer(@PathVariable(name = "gameId")int gameId) {
        return games.get(gameId).getSseEmitter();
    }

    //ok
    @GetMapping("initialState/gameId={gameId}")
    public ResponseEntity<Game> gameInitialState(@PathVariable(name = "gameId")int gameId){
        return ResponseEntity.ok(games.get(gameId));
    }

    //ok
    @PostMapping("join/gameId={gameId}/playerName={playerName}")
    public ResponseEntity<Player> joinGame(@PathVariable("gameId")int gameId, @PathVariable("playerName")String playerName) throws IOException {
        return ResponseEntity.ok(games.get(gameId).addPlayer(playerName));
    }
}
