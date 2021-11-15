package com.example.models.strategy;

import com.example.models.Player;

/** Stratégie Toujours coopérer */
final class CooperateStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return 1;
    }
}
