package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

@Getter
public class Player {
    @Getter(AccessLevel.NONE)
    private static int playerCounter;
    private final int id;
    private IStrategy strategy;
    private StrategyChoice currentChoice = StrategyChoice.NONE;
    private final Deque<StrategyChoice> choicesHistory = new ArrayDeque<>();
    private final PlayerScore score = new PlayerScore();
    private final String name;
    private boolean canPlay;

    public Player(String name) {
        id = ++playerCounter;
        this.name = name;
        canPlay = true;
    }

    public Player(String name, IStrategy strategy) {
        this(name);
        this.strategy = strategy;
    }

    public StrategyChoice strategyPlay(StrategyExecutionData executionData) throws StrategyException {
        if (strategy != null) {
            StrategyChoice choice = strategy.execute(executionData);
            play(choice);
            return choice;
        } else {
            throw new StrategyException("strategy not found");
        }
    }

    public void play(StrategyChoice choice) {
        currentChoice = choice;
        canPlay = false;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean hasStrategy() {
        return strategy != null;
    }

    public StrategyChoice getLastChoice() {
        try {
            return choicesHistory.getLast();
        } catch (NoSuchElementException e){
            return StrategyChoice.NONE;
        }
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

    public String getName() {
        return name;
    }

    public boolean canPlay() {
        return canPlay;
    }

    public void allowToPlay() {
        canPlay = true;
    }

    public void disallowToPlay() {
        canPlay = false;
    }

    public int getChoiceCount(StrategyChoice playerChoice) {
        BiFunction<Integer, StrategyChoice, Integer> countDefectReducer =
                (counter, choice) -> counter + (choice == playerChoice ? 1 : 0);
        return choicesHistory.stream().reduce(0, countDefectReducer, Integer::sum);
    }
}
