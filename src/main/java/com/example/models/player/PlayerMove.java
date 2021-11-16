package com.example.models.player;

import com.example.models.player.Player;

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
