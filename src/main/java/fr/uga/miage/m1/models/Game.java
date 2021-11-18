package fr.uga.miage.m1.models;

import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;
import fr.uga.miage.m1.models.player.PlayerMove;
import fr.uga.miage.m1.models.player.PlayerScore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Getter
@Setter
public class Game {
    private static int gameCounter;
    private int id;
    private Player player1;
    private Player player2;
    private List<PlayerMove> moveHistory = new ArrayList<>();
    @Getter(AccessLevel.NONE)
    private int turnCount = 0;
    private int maxTurnCount;
    @Getter(AccessLevel.NONE)
    private SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
    private boolean allPlayerAreHere;
    @Getter(AccessLevel.NONE)
    private static final Logger LOGGER = Logger.getLogger(Game.class.getPackageName());

    public Game(Player player1, Player player2, int maxTurnCount) {
        id = ++gameCounter;
        this.player1 = player1;
        this.player2 = player2;
        this.maxTurnCount = maxTurnCount;
        allPlayerAreHere = !(player1 == null || player2 == null);
        sseEmitter.onCompletion(() -> LOGGER.info(() -> String.format("SSE emitter is completed. (gameId = %d)%n", id)));
        sseEmitter.onTimeout(() -> LOGGER.warning(() -> String.format("SSE emitter timed out. (gameId = %d)%n", id)));
        sseEmitter.onError(ex -> LOGGER.severe(() -> String.format("SSE emitter got an error (gameId = %d) : %s%n", id, ex)));
    }

    public int getId() {
        return id;
    }

    public void aiTakeTurn(Player aiPlayer) throws Exception {
        Player otherPlayer = aiPlayer == player1 ? player2 : player1;
        PlayerChoice choice = aiPlayer.strategyPlay(turnCount, otherPlayer);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        aiPlayer.canPlay = false;
        LOGGER.info(() ->
                String.format("%s a %s.", aiPlayer.name, (choice == PlayerChoice.COOPERATE ? "coopéré" : "trahi"))
        );
    }

    public synchronized void humanTakeTurn(Player humanPlayer, PlayerChoice choice) {
        humanPlayer.manualPlay(choice);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        humanPlayer.canPlay = false;
        LOGGER.info(() ->
                String.format("%s a %s.", humanPlayer.name, (choice == PlayerChoice.COOPERATE ? "coopéré" : "trahi"))
        );
    }

    private void calculateTurnScore() {
        PlayerChoice player1Choice = player1.getCurrentChoice();
        PlayerChoice player2Choice = player2.getCurrentChoice();

        if (player1Choice != PlayerChoice.NONE && player2Choice != PlayerChoice.NONE) {
            switch (player1Choice.ordinal() + player2Choice.ordinal()) {
                case 0:
                    PlayerScore.applyBothDefected(player1, player2);
                    break;
                case 1:
                    if (player1Choice == PlayerChoice.DEFECT) {
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
        return !player1.canPlay && !player2.canPlay;
    }

    public void endTurn() {
        calculateTurnScore();
        player1.updateChoicesHistory();
        player2.updateChoicesHistory();
        player1.canPlay = true;
        player2.canPlay = true;
    }

    public void testTurn() throws Exception {
        turnCount++;
        aiTakeTurn(player1);
        aiTakeTurn(player2);
        endTurn();
    }

    public void testLaunch() {
        if (maxTurnCount > 0) {
            try {
                while (turnCount < maxTurnCount) {
                    LOGGER.info(() -> String.format("<<< Tour %d >>>", turnCount + 1));
                    testTurn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOGGER.info(() -> String.format(
                "SCORES :%n%s : %d ; %s : %d", player1.name, player1.getScore(), player2.name, player2.getScore()
        ));
    }

    public void setPlayer(Player player) {
        if (player1 == null) {
            player1 = player;
        } else {
            player2 = player;
        }
    }

    public boolean areAllPlayersHere() {
        return player1 != null && player2 != null;
    }

    public Player getPlayerWithId(int id) {
        if (player1.getId() == id) {
            return player1;
        }
        if (player2.getId() == id) {
            return player2;
        }
        return null;
    }

    public synchronized Game playMove(int playerId, PlayerChoice move) throws Exception {
        Player player = getPlayerWithId(playerId);
        Player otherPlayer = player == player1 ? player1 : player2;
        if (otherPlayer.getStrategy() != null) {
            aiTakeTurn(otherPlayer);
        }
        humanTakeTurn(player, move);
        if (canEndTurn()) sseEmitter.send(this);
        return this;
    }

    public synchronized Player addPlayer(String playerName) throws IOException {
        Player p = new Player(playerName, null);
        setPlayer(p);
        if (areAllPlayersHere()) sseEmitter.send(this);
        return p;
    }

    public SseEmitter getSseEmitter() {
        return sseEmitter;
    }
}
