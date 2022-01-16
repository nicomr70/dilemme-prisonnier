package fr.uga.miage.m1.interfaces;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Collection;

@Component
public interface GameServiceI {

    SseEmitter newEmitterAllGames();
    void createGame(int maxTurnCount) throws GameException;
    Collection<Game> getAllGame();
    SseEmitter getNewEmitterWaitLastPlayer(int gameId);
    SseEmitter getNewEmitterWaitPlayerPlay(int gameId);
    SseEmitter getNewEmitterViewGame(int gameId);
    Game getGame(int gameId);
    Collection<String> getAllStrategy();
    StrategyChoice[] getAllMoves();
    void setStrategyForPlayerId(String strategy,int gameId,int playerId) throws StrategyException;




}
