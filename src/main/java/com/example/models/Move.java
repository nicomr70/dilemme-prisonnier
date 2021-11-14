package com.example.models;

public class Move {
    public Player player;
    public byte choice;
    public int turnNumber;

    public Move(Player player, byte choice, int turnNumber) {
        this.player = player;
        this.choice = choice;
        this.turnNumber = turnNumber;
    }
}
