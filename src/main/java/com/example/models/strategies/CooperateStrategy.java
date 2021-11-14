package com.example.models.strategies;

import com.example.models.Player;
import com.example.models.Strategy;

/** Stratégie Toujours coopérer */
public final class CooperateStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return 1;
    }
}
