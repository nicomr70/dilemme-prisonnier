package com.example.requests;

import com.example.models.Game;
import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;
import com.example.models.strategy.StrategyType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.example.RestServer.games;
import static java.lang.Thread.sleep;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class HttpRequestGame {

    static SseEmitter emitter = new SseEmitter();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }

    /*@GetMapping("/waitPlayerPlay/gameId={gameId}")
    synchronized ResponseEntity<Game> waitPlayerPlay(@PathVariable(name = "gameId")int gameId) throws InterruptedException {
        return ResponseEntity.ok(games.get(gameId).waitPlayerPLay());
    }*/

    @PostMapping("/play/gameId={gameId}/playerId={playerId}/move={move}")
    synchronized ResponseEntity<Game> playMove(@PathVariable(name = "gameId")int gameId, @PathVariable(name = "playerId")int playerId, @PathVariable(name = "move") PlayerChoice move) throws IOException {
        //TODO savoir sur quel partie on joue (id)
        return ResponseEntity.ok(games.get(gameId).playMove(playerId,move));
    }

    @GetMapping("/allStrategy")
    public ResponseEntity<Object[]> allStrategy(){
        return ResponseEntity.ok(Arrays.stream(StrategyType.values()).map(StrategyType::getName).toArray());
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
    public SseEmitter streamTest(@PathVariable(name = "gameId")int gameId){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> System.out.println("SseEmitter is completed"));
        sseEmitter.onTimeout(() -> System.out.println("SseEmitter is timed out"));
        sseEmitter.onError((ex) -> System.out.println("SseEmitter got error:"+ex));
        games.get(gameId).setSse(sseEmitter);
        return sseEmitter;
    }
}
