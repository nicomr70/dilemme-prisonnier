package com.example.models.strategies;

import com.example.models.Player;
import com.example.models.Strategy;

/** Stratégie Donnant-donnant */
public final class TitForTatStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return 1;
        } else {
            return otherPlayer.getLastChoice();
        }
    }
}
