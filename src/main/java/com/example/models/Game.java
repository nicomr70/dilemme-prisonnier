package com.example.models;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;
import com.example.models.player.PlayerMove;
import com.example.models.player.PlayerScore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO faire une fonction qui renvoie un json en string et qui represente le jeu au complet, utiliser JSONObject
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
    private SseEmitter sse;
    private boolean allPlayerIsHere;

    public Game(Player player1, Player player2, int maxTurnCount) {
        id = ++gameCounter;
        this.player1 = player1;
        this.player2 = player2;
        moveHistory = new ArrayList<>();
        this.maxTurnCount = maxTurnCount;
        this.allPlayerIsHere = !(player1==null || player2==null);

    }

    public int getId() {
        return id;
    }

    public PlayerChoice AITakeTurn(Player AIPlayer) throws Exception {
        Player otherPlayer = AIPlayer == player1 ? player2 : player1;
        PlayerChoice choice = AIPlayer.strategyPlay(turnCount, otherPlayer);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        System.out.println(AIPlayer.name + " a " + (choice == PlayerChoice.COOPERATE ? "coopéré" : "trahi") + "."); // To be removed in final version
        AIPlayer.canPlay = false;
        return choice;
    }

    // TODO : synchronized car deux joueur ne peuvent pas jouer en même temsp , et pas mis dans httpRequest car deja une variable de condition
    public synchronized PlayerChoice humanTakeTurn(Player humanPlayer, PlayerChoice choice) {
        humanPlayer.manualPlay(choice);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        System.out.println(humanPlayer.name + " a " + (choice == PlayerChoice.COOPERATE ? "coopéré" : "trahi") + "."); // To be removed in final version
        humanPlayer.canPlay = false;
        return choice;
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

    public void setPlayer(Player player){
        if (player1 == null) { player1 = player; }
        else { player2 = player; }
    }

    public boolean areAllPlayersHere() {
        return player1 != null && player2 != null;
    }

    public Player getPlayerWithId(int id) {
        if (player1.getId() == id) { return player1; }
        if (player2.getId()==id) { return player2; }
        return null;
    }


    public Game waitSecondPlayer() throws InterruptedException {
        while(!(areAllPlayersHere()))wait();
        return this;
    }

    synchronized public Game playMove(int playerId,PlayerChoice move) throws IOException {
        //TODO verifier aue l'autre joueur a une strategie en place, si oui alors le faire jouer et notifier
        humanTakeTurn(getPlayerWithId(playerId), move);
        if(canEndTurn())sse.send(this);
        return this;
    }

    synchronized public Player addPlayer(String playerName) throws IOException {
        Player p = new Player(playerName, null);
        setPlayer(p);
        if(areAllPlayersHere())sse.send(this);
        return p;
    }
}
