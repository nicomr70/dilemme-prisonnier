package com.example.models.strategies;

import com.example.models.Player;
import com.example.models.Strategy;

/** Stratégie Toujours trahir */
public final class DefectStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return 0;
    }
}
