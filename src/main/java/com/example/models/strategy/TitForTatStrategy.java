package com.example.models.strategy;

import com.example.models.Player;

/** Stratégie Donnant-donnant */
final class TitForTatStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return 1;
        } else {
            return otherPlayer.getLastChoice();
        }
    }
}
