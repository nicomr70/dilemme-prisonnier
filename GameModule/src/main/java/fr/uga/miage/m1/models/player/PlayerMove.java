package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.sharedstrategy.StrategyChoice;

public class PlayerMove {
    private final Player player;
    private final StrategyChoice choice;
    private final int turnNumber;

    public PlayerMove(Player player, StrategyChoice choice, int turnNumber) {
        this.player = player;
        this.choice = choice;
        this.turnNumber = turnNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public StrategyChoice getChoice() {
        return choice;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
