package fr.uga.miage.m1.models.game;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.strategy.StrategyFactory;
import fr.uga.miage.m1.service.IGameService;
import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collection;

@DisplayName("Testing IGameService")
class GameServiceTest {
    IGameService gameService = new Game.GameService();

    @Test
    void newEmitterAllGames(){
        SseEmitter emitter = gameService.newEmitterAllGames();
        Assertions.assertNotNull(emitter);
        Assertions.assertEquals(Long.MAX_VALUE,emitter.getTimeout());
    }
    @Test
    void createGame() throws GameException {
        int sizePreview = gameService.getAllGame().size();
        gameService.createGame(10);
        Assertions.assertEquals(gameService.getAllGame().size(),sizePreview+1);
    }

    @Test
    void createGameWithException(){
        Assertions.assertThrows(GameException.class,()->gameService.createGame(-10));
    }

    @Test
    void getAllGame() throws GameException {
        gameService.createGame(10);
        Collection<Game> games = gameService.getAllGame();
        Assertions.assertTrue(games.size()>0);
    }

    @Test
    void getNewEmitterWaitLastPlayer(){
        SseEmitter emitter = gameService.newEmitterAllGames();
        Assertions.assertNotNull(emitter);
        Assertions.assertEquals(emitter.getTimeout(),Long.MAX_VALUE);
    }

    @Test
    void  getNewEmitterWaitPlayerPlay(){
        SseEmitter emitter = gameService.newEmitterAllGames();
        Assertions.assertNotNull(emitter);
        Assertions.assertEquals(Long.MAX_VALUE,emitter.getTimeout());
    }

    @Test
    void getNewEmitterViewGame(){
        SseEmitter emitter = gameService.newEmitterAllGames();
        Assertions.assertNotNull(emitter);
        Assertions.assertEquals(Long.MAX_VALUE,emitter.getTimeout());
    }
    @Test
    void getGame() throws GameException {
        gameService.createGame(10);
        Game[] games = gameService.getAllGame().toArray(Game[]::new);
        Game g = games[games.length-1];
        Assertions.assertEquals(10,g.getMaxTurnCount());
        Assertions.assertNull(g.getPlayer1());
        Assertions.assertNull(g.getPlayer2());
        Assertions.assertEquals(g.getMoveHistory(),new ArrayList<>());
    }

    @Test
    void getAllStrategy(){
        String[] strategies =  gameService.getAllStrategy().toArray(String[]::new);
        Assertions.assertArrayEquals(strategies, StrategyFactory.getSTRATEGIES_FULL_NAME().keySet().toArray(String[]::new));
    }

    @Test
    void getAllMoves(){
        StrategyChoice[] choices = gameService.getAllMoves();
        Assertions.assertArrayEquals(choices,StrategyChoice.values());
    }

    @Test
    void setStrategyForPlayerId() throws StrategyException, GameException {
        gameService.createGame(10);
        Game[] games = gameService.getAllGame().toArray(Game[]::new);
        Game g = games[games.length-1];
        g.addPlayer(new Player("Nicolas"));
        g.addPlayer(new Player("Alexis"));
        gameService.setStrategyForPlayerId(StrategyFactory.getSTRATEGIES_FULL_NAME().get("PAVLOV"), g.getId(),g.getPlayer1().getId());
        Assertions.assertEquals("Nicolas",g.getPlayer1().getName());
        Assertions.assertEquals("Alexis",g.getPlayer2().getName());
        Assertions.assertInstanceOf(RandomStrategy.class,g.getPlayer1().getStrategy());
    }
}
