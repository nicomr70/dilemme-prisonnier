package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.models.strategy.IStrategy;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.BiFunction;

@Getter
public class Player {
    @Getter(AccessLevel.NONE)
    static int playerCounter;
    private final int id;
    private IStrategy strategy;
    private PlayerChoice currentChoice = PlayerChoice.NONE;
    private final Stack<PlayerChoice> choicesHistory;
    private final PlayerScore score;

    public String name;
    public boolean canPlay;


    public Player(String name) {
        id = ++playerCounter;
        score = new PlayerScore();
        choicesHistory = new Stack<>();
        this.name = name;
        canPlay = true;
    }

    public Player(String name, IStrategy strategy) {
        this(name);
        this.strategy = strategy;
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

    public void increaseScore(int earnedPoints) {
        score.addToValue(earnedPoints);
    }

    public int getId() {
        return id;
    }

    public int getChoiceCount(PlayerChoice playerChoice) {
        BiFunction<Integer, PlayerChoice, Integer> countDefectReducer =
                (counter, choice) -> counter + (choice == playerChoice ? 1 : 0);
        return choicesHistory.stream().reduce(0, countDefectReducer, Integer::sum);
    }
}
