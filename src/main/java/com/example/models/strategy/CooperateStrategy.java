package com.example.models.strategy;

import com.example.models.player.Player;

/** Stratégie Toujours coopérer */
final class CooperateStrategy implements IStrategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return 1;
    }
}
