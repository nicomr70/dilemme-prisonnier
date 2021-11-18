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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Game {
    public static Map<Integer, Game> games = new HashMap<>();
    private static int gameCounter;
    private int id;
    private Player player1;
    private Player player2;
    private List<PlayerMove> moveHistory;
    @Getter(AccessLevel.NONE)
    private int turnCount = 0;
    private int maxTurnCount;
    @Getter(AccessLevel.NONE)
    private SseEmitter sseEmitter;
    private boolean allPlayerAreHere;

    public Game(Player player1, Player player2, int maxTurnCount) {
        id = ++gameCounter;
        this.player1 = player1;
        this.player2 = player2;
        moveHistory = new ArrayList<>();
        this.maxTurnCount = maxTurnCount;
        allPlayerAreHere = !(player1 == null || player2 == null);
        sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onCompletion(() -> System.out.println("SseEmitter of game " + id + " is completed."));
        sseEmitter.onTimeout(() -> System.out.println("SseEmitter of game " + id + " is timed out."));
        sseEmitter.onError((ex) -> System.err.println("SseEmitter of game " + id + " got error : " + ex));
    }

    public int getId() {
        return id;
    }

    public void AITakeTurn(Player AIPlayer) throws Exception {
        Player otherPlayer = AIPlayer == player1 ? player2 : player1;
        PlayerChoice choice = AIPlayer.strategyPlay(turnCount, otherPlayer);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        System.out.println(AIPlayer.name + " a " + (choice == PlayerChoice.COOPERATE ? "coopéré" : "trahi") + "."); // To be removed in final version
        AIPlayer.canPlay = false;
    }

    public synchronized void humanTakeTurn(Player humanPlayer, PlayerChoice choice) {
        humanPlayer.manualPlay(choice);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        System.out.println(humanPlayer.name + " a " + (choice == PlayerChoice.COOPERATE ? "coopéré" : "trahi") + "."); // To be removed in final version
        humanPlayer.canPlay = false;
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

    public void turn() throws Exception {
        turnCount++;
        AITakeTurn(player1);
        AITakeTurn(player2);
        endTurn();
    }

    public void launch() {
        if (maxTurnCount > 0) {
            try {
                while (turnCount < maxTurnCount) {
                    System.out.println("<<< Tour " + (turnCount + 1) + " >>>");
                    turn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("SCORES :\n" + player1.name + " : " + player1.getScore() + " ; " + player2.name + " : " + player2.getScore());
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

    synchronized public Game playMove(int playerId, PlayerChoice move) throws Exception {
        Player player = getPlayerWithId(playerId);
        Player otherPlayer = player == player1 ? player1 : player2;
        if (otherPlayer.getStrategy() != null) {
            AITakeTurn(otherPlayer);
        }
        humanTakeTurn(player, move);
        if (canEndTurn()) sseEmitter.send(this);
        return this;
    }

    synchronized public Player addPlayer(String playerName) throws IOException {
        Player p = new Player(playerName, null);
        setPlayer(p);
        if (areAllPlayersHere()) sseEmitter.send(this);
        return p;
    }

    public SseEmitter getSseEmitter() {
        return sseEmitter;
    }
}
