package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.strategy.StrategyFactory;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import lombok.extern.java.Log;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
@CrossOrigin
@RestController
@RequestMapping("/game")
@Log
public class HttpRequestGame {

    @GetMapping("/waitPlayerPlay/gameId={gameId}")
    public SseEmitter waitPlayerPlay(@PathVariable(name = "gameId")int gameId) {
        return Game
                .gamePool
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
        Game g = Game.gamePool.getGame(gameId);
        if(g == null){
            return ResponseEntity.status(404).body(null);
        }else{
            return ResponseEntity.ok(Game.gamePool.getGame(gameId).takeTurn(playerId, move));
        }

    }

    @GetMapping("/allStrategies")
    public ResponseEntity<Collection<String>> allStrategies(){
        return ResponseEntity.ok(StrategyFactory.getSTRATEGIES_FULL_NAME().keySet());
    }

    @PutMapping("/{gameId}/{playerId}/{strategy}")
    public void setStrategyPlayer(@PathVariable("gameId")int gameId ,
                                  @PathVariable("playerId")int playerId,
                                  @PathVariable("strategy")String strategy) throws StrategyException {
        IStrategy s= StrategyFactory.getStrategyFromType(StrategyFactory.getSTRATEGIES_FULL_NAME().get(strategy));
        Game.gamePool.getGame(gameId).getPlayerById(playerId).setStrategy(s);
    }

    @GetMapping("/allMoves")
    public ResponseEntity<StrategyChoice[]> allMoves(){
        return ResponseEntity.ok(StrategyChoice.values());
    }

    @GetMapping("waitLastPlayer/gameId={gameId}")
    public SseEmitter waitLastPlayer(
            @PathVariable(name = "gameId") int gameId
    ) {
        return Game
                .gamePool
                .getGame(gameId)
                .poolWaitPlayer
                .newEmitter("wait player (gameId="+gameId+")");
    }

    @GetMapping("initialState/gameId={gameId}")
    public ResponseEntity<Game> gameInitialState(
            @PathVariable(name = "gameId") int gameId
    ){
        Game g = Game.gamePool.getGame(gameId);
        if(g == null) {
            return ResponseEntity.status(404).body(null);
        }else{
            return ResponseEntity.ok(g);
        }
    }

    @PutMapping("join/gameId={gameId}/playerName={playerName}")
    public ResponseEntity<Player> joinGame(
            @PathVariable("gameId") int gameId,
            @PathVariable("playerName") String playerName
    ) {
        Game g = Game.gamePool.getGame(gameId);
        if(g == null){
            return ResponseEntity.status(404).body(null);
        }else{
            Player joiningPlayer = new Player(playerName);
            return ResponseEntity.ok(g.addPlayer(joiningPlayer));
        }
    }

    @GetMapping("/viewGame/{gameId}")
    public SseEmitter getSseviewGame(@PathVariable("gameId")int gameId){
        return Game.gamePool.getGame(gameId).poolViewGame.newEmitter("view game (gameId ="+gameId+")");
    }

    @GetMapping("{gameId}/player/{playerId}")
    public ResponseEntity<Player> player(
            @PathVariable(name ="gameId") int gameId,
            @PathVariable(name = "playerId")int playerId
    ) {
        Game g = Game.gamePool.getGame(gameId);
        if(g==null){
            return ResponseEntity.status(404).body(null);
        }else{
            Player p = g.getPlayerById(playerId);
            if(p == null ){
                return ResponseEntity.status(404).body(null);
            }else{
                return ResponseEntity.ok(p);
            }
        }
    }
}
