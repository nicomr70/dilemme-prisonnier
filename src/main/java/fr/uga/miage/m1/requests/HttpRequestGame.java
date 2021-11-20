package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.RestServer;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;
import fr.uga.miage.m1.models.strategy.StrategyType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/game")
public class HttpRequestGame {
    @GetMapping("/waitPlayerPlay/gameId={gameId}")
    public SseEmitter waitPlayerPlay(@PathVariable(name = "gameId")int gameId) {
        return RestServer
                .getGamePool()
                .getGame(gameId)
                .poolPlayGame
                .sseEmitterFactory("wait player play (gameId ="+gameId+")");
    }

    @PutMapping("/play/gameId={gameId}/playerId={playerId}/move={move}")
    public synchronized ResponseEntity<Game> playMove(
            @PathVariable(name = "gameId") int gameId,
            @PathVariable(name = "playerId") int playerId,
            @PathVariable(name = "move") PlayerChoice move
    ) throws StrategyException, IOException {
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).playMove(playerId, move));
    }

    @GetMapping("/allStrategies")
    public ResponseEntity<Object[]> allStrategies(){
        return ResponseEntity.ok(Arrays.stream(StrategyType.values()).map(StrategyType::getName).toArray());
    }

    @GetMapping("/allMoves")
    public ResponseEntity<PlayerChoice[]> allMoves(){
        return ResponseEntity.ok(PlayerChoice.values());
    }

    @GetMapping("waitLastPlayer/gameId={gameId}")
    public SseEmitter waitLastPlayer(
            @PathVariable(name = "gameId") int gameId
    ) {
        return RestServer
                .getGamePool()
                .getGame(gameId)
                .poolWaitPlayer
                .sseEmitterFactory("wait player (gameId="+gameId+")");
    }

    @GetMapping("initialState/gameId={gameId}")
    public ResponseEntity<Game> gameInitialState(
            @PathVariable(name = "gameId") int gameId
    ){
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId));
    }

    @PutMapping("join/gameId={gameId}/playerName={playerName}")
    public ResponseEntity<Player> joinGame(
            @PathVariable("gameId") int gameId,
            @PathVariable("playerName")String playerName
    ) throws IOException {
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).addPlayer(playerName));
    }


    @GetMapping("{gameId}/player/{playerId}")
    public ResponseEntity<Player> player(@PathVariable(name ="gameId") int gameId,@PathVariable(name = "playerId")int playerId){
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).getPlayerWithId(playerId));
    }
}
