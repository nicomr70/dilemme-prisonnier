package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

public interface IStrategy {
    PlayerChoice execute(int turnCount, Player player, Player otherPlayer);
}
