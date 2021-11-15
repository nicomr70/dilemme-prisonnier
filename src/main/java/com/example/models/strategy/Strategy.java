package com.example.models.strategy;

import com.example.models.Player;

public interface Strategy {
    byte execute(int turnCount, Player player, Player otherPlayer);
}
