package fr.uga.miage.m1.models.player;

public class PlayerMove {
    private Player player;
    private PlayerChoice choice;
    private int turnNumber;

    public PlayerMove(Player player, PlayerChoice choice, int turnNumber) {
        this.player = player;
        this.choice = choice;
        this.turnNumber = turnNumber;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerChoice getChoice() {
        return choice;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
