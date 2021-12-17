package fr.uga.miage.m1.models.game;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerMove;
import fr.uga.miage.m1.models.player.PlayerScore;
import fr.uga.miage.m1.requests.HttpRequest;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;
import fr.uga.miage.m1.utils.SseEmitterPool;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

@Getter
@Setter
public class Game {
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

    public Game(int maxTurnCount) {
        id = ++gameCounter;
        this.maxTurnCount = maxTurnCount;
        poolPlayGame = new SseEmitterPool();
        poolWaitPlayer = new SseEmitterPool();
    }

    public Game(int maxTurnCount, Player player1, Player player2) {
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
        }
        return this;
    }

    public synchronized Player addPlayer(Player player) {
        setPlayer(player);
        if (areAllPlayersHere()) {
            poolWaitPlayer.sendAll(this);
        }
        HttpRequest.updateAllGames();
        return player;
    }
}
