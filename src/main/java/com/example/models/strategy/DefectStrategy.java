package com.example.models.strategy;

import com.example.models.Player;

/** Stratégie Toujours trahir */
final class DefectStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return 0;
    }
}
