package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.RestServer;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import lombok.extern.java.Log;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;

@CrossOrigin
@RestController
@RequestMapping("/game")
@Log
public class HttpRequestGame {

    @GetMapping("/waitPlayerPlay/gameId={gameId}")
    public SseEmitter waitPlayerPlay(@PathVariable(name = "gameId")int gameId) {
        return RestServer
                .getGamePool()
                .getGame(gameId)
                .poolPlayGame
                .newEmitter("wait player play (gameId ="+gameId+")");
    }

    @PutMapping("/play/gameId={gameId}/playerId={playerId}/move={move}")
    public synchronized ResponseEntity<Game> playMove(
            @PathVariable(name = "gameId") int gameId,
            @PathVariable(name = "playerId") int playerId,
            @PathVariable(name = "move") StrategyChoice move
    ) throws StrategyException {
        log.info("game id : "+ gameId + " playerId : "+playerId+ " move : "+move);
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).takeTurn(playerId, move));
    }

    @GetMapping("/allStrategies")
    public ResponseEntity<Object[]> allStrategies(){
       // TODO: return ResponseEntity.ok(Arrays.stream(StrategyType.values()).map(StrategyType::getName).toArray());
        return ResponseEntity.ok(new Object[]{});
    }

    @GetMapping("/allMoves")
    public ResponseEntity<StrategyChoice[]> allMoves(){
        return ResponseEntity.ok(StrategyChoice.values());
    }

    @GetMapping("waitLastPlayer/gameId={gameId}")
    public SseEmitter waitLastPlayer(
            @PathVariable(name = "gameId") int gameId
    ) {
        return RestServer
                .getGamePool()
                .getGame(gameId)
                .poolWaitPlayer
                .newEmitter("wait player (gameId="+gameId+")");
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
            @PathVariable("playerName") String playerName
    ) {
        Player joiningPlayer = new Player(playerName);
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).addPlayer(joiningPlayer));
    }


    @GetMapping("{gameId}/player/{playerId}")
    public ResponseEntity<Player> player(
            @PathVariable(name ="gameId") int gameId,
            @PathVariable(name = "playerId")int playerId
    ) {
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).getPlayerById(playerId));
    }
}
