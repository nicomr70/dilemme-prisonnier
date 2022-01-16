package fr.uga.miage.m1.requests;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.interfaces.GameServiceI;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.strategy.StrategyFactory;
import lombok.extern.java.Log;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;
@CrossOrigin
@RestController
@RequestMapping("/game")
@Log
public class HttpRequestGame {

    @Autowired
    GameServiceI gameService;

    @GetMapping("/waitPlayerPlay/gameId={gameId}")
    public SseEmitter waitPlayerPlay(@PathVariable(name = "gameId")int gameId) {
        return gameService.getNewEmitterWaitPlayerPlay(gameId);
    }

    @PutMapping("/play/gameId={gameId}/playerId={playerId}/move={move}")
    public synchronized ResponseEntity<Game> playMove(
            @PathVariable(name = "gameId") int gameId,
            @PathVariable(name = "playerId") int playerId,
            @PathVariable(name = "move") StrategyChoice move
    ) throws StrategyException {
        Game g = gameService.getGame(gameId);
        if(g == null){
            return ResponseEntity.status(404).body(null);
        }else{
            return ResponseEntity.ok(g.takeTurn(playerId, move));
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
       gameService.setStrategyForPlayerId(strategy,playerId,playerId);
    }

    @GetMapping("/allMoves")
    public ResponseEntity<StrategyChoice[]> allMoves(){
        return ResponseEntity.ok(gameService.getAllMoves());
    }

    @GetMapping("waitLastPlayer/gameId={gameId}")
    public SseEmitter waitLastPlayer(
            @PathVariable(name = "gameId") int gameId
    ) {
        return gameService.getNewEmitterWaitLastPlayer(gameId);
    }

    @GetMapping("initialState/gameId={gameId}")
    public ResponseEntity<Game> gameInitialState(
            @PathVariable(name = "gameId") int gameId
    ){
        Game g = gameService.getGame(gameId);
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
        Game g = gameService.getGame(gameId);
        if(g == null){
            return ResponseEntity.status(404).body(null);
        }else{
            Player joiningPlayer = new Player(playerName);
            return ResponseEntity.ok(g.addPlayer(joiningPlayer));
        }
    }

    @GetMapping("/viewGame/{gameId}")
    public SseEmitter getSseViewGame(@PathVariable("gameId")int gameId){
        return gameService.getNewEmitterViewGame(gameId);
    }

    @GetMapping("{gameId}/player/{playerId}")
    public ResponseEntity<Player> player(
            @PathVariable(name ="gameId") int gameId,
            @PathVariable(name = "playerId")int playerId
    ) {
        Game g = gameService.getGame(gameId);
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
