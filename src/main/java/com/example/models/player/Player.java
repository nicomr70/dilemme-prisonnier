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
    private byte currentChoice = -1;
    private Stack<Byte> choicesHistory;
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

    public byte strategyPlay(int turnCount, Player otherPlayer) throws Exception {
        if (strategy != null) {
            byte choice = strategy.execute(turnCount, this, otherPlayer);
            currentChoice = choice;
            canPlay = false;
            return choice;
        } else {
            throw new Exception("player has no strategy");
        }
    }

    public void manualPlay(byte choice) {
        currentChoice = choice;
        canPlay = false;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public byte getLastChoice() {
        try {
            return choicesHistory.peek();
        } catch (EmptyStackException e){
            e.printStackTrace();
        }
        return -1;
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