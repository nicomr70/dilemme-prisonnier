package com.example.models;

import com.example.models.player.Player;
import com.example.models.player.PlayerMove;
import com.example.models.player.PlayerScore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//TODO faire une fonction qui renvoie un json en string et qui represente le jeu au complet, utiliser JSONObject
@Getter
@Setter
public class Game {
    private static int gameCounter;
    private int id;
    private Player player1;
    private Player player2;
    private List<PlayerMove> moveHistory;
    @Getter(AccessLevel.NONE)
    private int turnCount = 0;
    private int maxTurnCount;

    public Game(Player player1, Player player2,int maxTurnCount) {
        id = ++gameCounter;
        this.player1 = player1;
        this.player2 = player2;
        moveHistory = new ArrayList<>();
        this.maxTurnCount=maxTurnCount;

    }

    public int getId() {
        return id;
    }

    public byte AITakeTurn(Player AIPlayer) throws Exception {
        Player otherPlayer = AIPlayer == player1 ? player2 : player1;
        byte choice = AIPlayer.strategyPlay(turnCount, otherPlayer);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        System.out.println(AIPlayer.name + " a " + (choice == 1 ? "coopéré" : "trahi") + "."); // To be removed in final version
        AIPlayer.canPlay = false;
        return choice;
    }

    // TODO : synchronized car deux joueur ne peuvent pas jouer en même temsp , et pas mis dans httpRequest car deja une variable de condition
    public synchronized byte humanTakeTurn(Player humanPlayer, byte choice) {
        humanPlayer.manualPlay(choice);
        PlayerMove move = new PlayerMove(player1, choice, turnCount);
        moveHistory.add(move);
        System.out.println(humanPlayer.name + " a " + (choice == 1 ? "coopéré" : "trahi") + "."); // To be removed in final version
        humanPlayer.canPlay = false;
        return choice;
    }

    private void calculateTurnScore() {
        byte player1Choice = player1.getCurrentChoice();
        byte player2Choice = player2.getCurrentChoice();

        switch (player1Choice + player2Choice) {
            case 0:
                PlayerScore.applyBothDefected(player1, player2);
                break;
            case 1:
                if (player1Choice == 0) {
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

    public void launch(int turnCount) {
        if (turnCount > 0) {
            try {
                for (int i = 0; i < turnCount; i++) {
                    System.out.println("<<< Tour " + (i+1) + " >>>");
                    turn();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("SCORES :\n" + player1.name + " : " + player1.getScore() + " ; " + player2.name + " : " + player2.getScore());
    }

    public void launch() {
        launch(10);
    }

    public JSONObject renvoiejeu(){
        JSONObject js = new JSONObject();
        js.put("id",this.id);
        /*js.put("player1",player1.getScore());
        /*js.put("player2",player2.getScore());
        /*s.add("'id' :" +this.id);
        s.add("'player1' :"+this.player1.getScore());
        s.add("'player2' :"+this.player2.getScore());*/
        return js;
    }

    public void setPlayer(Player player){
        if(player1==null){
            player1=player;
        }else{
            player2=player;
        }
    }

    public boolean areAllPlayersHere(){
        return player1!=null && player2!=null;
    }

    public Player getPlayerWithId(int id) {
        if(player1.getId()==id){
            return player1;
        }else if(player2.getId()==id){
            return player2;
        }else{
            return null;
        }
    }


}
