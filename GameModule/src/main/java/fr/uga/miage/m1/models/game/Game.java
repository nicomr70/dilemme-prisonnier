package fr.uga.miage.m1.models.game;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.service.IGameService;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerMove;
import fr.uga.miage.m1.models.player.PlayerScore;
import fr.uga.miage.m1.models.strategy.StrategyFactory;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;
import fr.uga.miage.m1.utils.SseEmitterPool;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.Objects.*;

@Getter
@Setter
@Log
public class Game{
    public static final String GAME_SERVICE="gameService";
    private static final SseEmitterPool poolAllGames = new SseEmitterPool();
    private static final GamePool gamePool = new GamePool();
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static int gameCounter;
    private int id;
    private Player player1;
    private Player player2;
    private List<PlayerMove> moveHistory = new ArrayList<>();
    private int turnCount;
    private final int maxTurnCount;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public SseEmitterPool poolPlayGame;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public SseEmitterPool poolWaitPlayer;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    public SseEmitterPool poolViewGame;

    public Game(int maxTurnCount) throws GameException {
        if(maxTurnCount>=2) {
            id = ++gameCounter;
            this.maxTurnCount = maxTurnCount;
            poolPlayGame = new SseEmitterPool();
            poolWaitPlayer = new SseEmitterPool();
            poolViewGame = new SseEmitterPool();
        }else{
            throw new GameException("max turn count not greater than 2 !");
        }
    }

    public Game(int maxTurnCount, Player player1, Player player2) throws GameException {
        this(maxTurnCount);
        this.player1 = player1;
        this.player2 = player2;
    }

    public int getId() {
        return id;
    }

    public void aiTakeTurn(Player aiPlayer) throws StrategyException {
        Player opposingPlayer = getOpposingPlayer(aiPlayer);
        StrategyExecutionData strategyExecutionData = new StrategyExecutionData()
                .setGameCurrentTurnCount(turnCount)
                .setMainPlayerScore(aiPlayer.getScore())
                .setMainPlayerPreviousScore(aiPlayer.getPreviousScore())
                .setMainPlayerChoicesHistory(aiPlayer.getChoicesHistory())
                .setOpposingPlayerScore(opposingPlayer.getScore())
                .setOpposingPlayerPreviousScore(opposingPlayer.getPreviousScore())
                .setOpposingPlayerChoicesHistory(opposingPlayer.getChoicesHistory());
        StrategyChoice choice = aiPlayer.strategyPlay(strategyExecutionData);
        updateMoveHistory(aiPlayer, choice);
        aiPlayer.disallowToPlay();
    }

    public synchronized void humanTakeTurn(Player humanPlayer, StrategyChoice choice) {
        humanPlayer.play(choice);
        updateMoveHistory(humanPlayer, choice);
        humanPlayer.disallowToPlay();
    }

    private void updateMoveHistory(Player player, StrategyChoice choice) {
        moveHistory.add(new PlayerMove(player, choice, turnCount));
    }

    private void calculateTurnScore() {
        StrategyChoice player1Choice = player1.getCurrentChoice();
        StrategyChoice player2Choice = player2.getCurrentChoice();

        if (player1Choice != null && player2Choice != null && !player1Choice.isNone() && !player2Choice.isNone()) {
            switch (player1Choice.ordinal() + player2Choice.ordinal()) {
                case 0:
                    PlayerScore.applyBothDefected(player1, player2);
                    break;
                case 1:
                    if (player1Choice.isDefect()) {
                        PlayerScore.applyOneDefectedOther(player1, player2);
                    } else {
                        PlayerScore.applyOneDefectedOther(player2, player1);
                    }
                    break;
                case 2:
                    PlayerScore.applyCooperated(player1, player2);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean canEndTurn() {
        return !player1.canPlay() && !player2.canPlay();
    }

    public void endTurn() {
        calculateTurnScore();
        player1.updateChoicesHistory();
        player2.updateChoicesHistory();
        player1.allowToPlay();
        player2.allowToPlay();
        turnCount++;
    }

    public void setPlayer(Player player) {
        if (player1 == null) {
            player1 = player;
        } else {
            player2 = player;
        }
    }

    public Player getOpposingPlayer(Player player) {
        if (player == player1) {
            return player2;
        }
        if (player == player2) {
            return player1;
        }
        return null;
    }

    public Player getPlayerById(int id) {
        if (player1.getId() == id) {
            return player1;
        }
        if (player2.getId() == id) {
            return player2;
        }
        return null;
    }

    public boolean areAllPlayersHere() {
        return player1 != null && player2 != null;
    }

    public synchronized Game takeTurn(int playerId, StrategyChoice choice) throws StrategyException {
        Player player = getPlayerById(playerId);
        Player opposingPlayer = getOpposingPlayer(player);
        if (requireNonNull(opposingPlayer).hasStrategy()) {
            aiTakeTurn(opposingPlayer);
        }
        humanTakeTurn(player, choice);
        if (canEndTurn()) {
            endTurn();
            poolPlayGame.sendAll(this);
            poolViewGame.sendAll(this);
        }
        return this;
    }

    public synchronized Player addPlayer(Player player) {
        setPlayer(player);
        if (areAllPlayersHere()) {
            poolWaitPlayer.sendAll(this);
        }
        updateAllGames();
        return player;
    }

    public static void updateAllGames() {
        poolAllGames.sendAll(gamePool.asCollection());
    }

    @Service
    public static class GameService implements IGameService {
        @Override
        public SseEmitter newEmitterAllGames() {
            return poolAllGames.newEmitter("AllGames");
        }

        @Override
        public void createGame(int maxTurnCount) throws GameException {
            Game g = new Game(maxTurnCount);
            Game.gamePool.registerGame(g);
            updateAllGames();

        }

        @Override
        public Collection<Game> getAllGame() {
            return gamePool.asCollection();
        }

        @Override
        public SseEmitter getNewEmitterWaitLastPlayer(int gameId) {
            return gamePool
                    .getGame(gameId)
                    .poolWaitPlayer
                    .newEmitter("wait player (gameId="+gameId+")");
        }

        @Override
        public SseEmitter getNewEmitterWaitPlayerPlay(int gameId) {
            return gamePool
                    .getGame(gameId)
                    .poolPlayGame
                    .newEmitter("wait player play (gameId ="+gameId+")");
        }

        @Override
        public SseEmitter getNewEmitterViewGame(int gameId) {
            return gamePool
                    .getGame(gameId)
                    .poolViewGame
                    .newEmitter("view game (gameId ="+gameId+")");
        }

        @Override
        public Game getGame(int gameId) {
            return gamePool.getGame(gameId);
        }

        @Override
        public Collection<String> getAllStrategy() {
            return StrategyFactory.getSTRATEGIES_FULL_NAME().keySet();
        }

        @Override
        public StrategyChoice[] getAllMoves() {
            return StrategyChoice.values();
        }

        @Override
        public void setStrategyForPlayerId(String strategy, int gameId, int playerId) throws StrategyException {
            IStrategy s= StrategyFactory.getStrategyFromType(StrategyFactory.getSTRATEGIES_FULL_NAME().get(strategy));
            Game.gamePool.getGame(gameId).getPlayerById(playerId).setStrategy(s);
        }
    }



}
