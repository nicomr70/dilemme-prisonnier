package com.example.models.player;

import com.example.models.player.Player;

public class PlayerMove {
    public Player player;
    public byte choice;
    public int turnNumber;

    public PlayerMove(Player player, byte choice, int turnNumber) {
        this.player = player;
        this.choice = choice;
        this.turnNumber = turnNumber;
    }
}
