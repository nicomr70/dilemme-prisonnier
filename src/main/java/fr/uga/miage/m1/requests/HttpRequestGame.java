package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.RestServer;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import lombok.extern.java.Log;
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
            @PathVariable(name = "playerId") int playerId//,
            @PathVariable(name = "move") PlayerChoice move
    ) throws StrategyException {
        log.info("game id : "+ gameId + " playerId : "+playerId+ " move : "+move);
        return ResponseEntity.ok(RestServer.getGamePool().getGame(gameId).takeTurn(playerId, move));
    }

    @PutMapping("/gameId={gameId}/playerId={playerId}/strategy={strategy}")
    public boolean setStrategyforPlayer(
            @PathVariable(name = "gameId")int gameId,
            @PathVariable(name="playerId")int playerId,
            @PathVariable(name ="strategy" )String strategy){
       Game g =  RestServer.getGamePool().getGame(gameId);
       Player player = g.getPlayerById(playerId);
       log.info("game recuperer : "+g.getId());
       log.info("player recuperer : "+player.getId());
       log.info("strategy reçu :"+strategy);
       return false;
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
