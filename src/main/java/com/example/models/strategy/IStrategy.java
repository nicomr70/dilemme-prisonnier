package com.example.models.strategy;

import com.example.models.player.Player;

public interface IStrategy {
    byte execute(int turnCount, Player player, Player otherPlayer);
}
