package com.example.models.player;

import com.example.models.strategy.IStrategy;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.EmptyStackException;
import java.util.Stack;

@Getter
public class Player {
    @Getter(AccessLevel.NONE)
    static int counter_player;
    private final int id;
    private IStrategy strategy;
    private PlayerChoice currentChoice = PlayerChoice.NONE;
    private Stack<PlayerChoice> choicesHistory;
    private final PlayerScore score;

    public boolean canPlay;
    public String name;


    public Player(String name) {
        this.id=++counter_player;
        this.name = name;
        score = new PlayerScore();
        canPlay = true;
    }

    public Player(String name, IStrategy strategy) {
        this(name);
        this.strategy = strategy;
        choicesHistory = new Stack<>();
    }

    public PlayerChoice strategyPlay(int turnCount, Player otherPlayer) throws Exception {
        if (strategy != null) {
            PlayerChoice choice = strategy.execute(turnCount, this, otherPlayer);
            currentChoice = choice;
            canPlay = false;
            return choice;
        } else {
            throw new Exception("player has no strategy");
        }
    }

    public void manualPlay(PlayerChoice choice) {
        currentChoice = choice;
        canPlay = false;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public PlayerChoice getLastChoice() {
        try {
            return choicesHistory.peek();
        } catch (EmptyStackException e){
            e.printStackTrace();
        }
        return PlayerChoice.NONE;
    }

    public boolean hasDefectedLastTurn() {
        return getLastChoice().isDefect();
    }

    public boolean hasCooperatedLastTurn() {
        return getLastChoice().isCooperate();
    }

    public void updateChoicesHistory() {
        choicesHistory.add(currentChoice);
    }

    public int getScore() {
        return score.getCurrentValue();
    }

    public int getPreviousScore() {
        return score.getPreviousValue();
    }

    public void updateScore(int earnedPoints) {
        score.addToValue(earnedPoints);
    }

    public int getId() {
        return id;
    }
}
