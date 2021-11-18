package fr.uga.miage.m1.models.player;

public class PlayerMove {
    public Player player;
    public PlayerChoice choice;
    public int turnNumber;

    public PlayerMove(Player player, PlayerChoice choice, int turnNumber) {
        this.player = player;
        this.choice = choice;
        this.turnNumber = turnNumber;
    }
}
