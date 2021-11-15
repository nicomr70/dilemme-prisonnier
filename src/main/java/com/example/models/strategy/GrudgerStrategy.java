package com.example.models.strategy;

import com.example.models.Player;

/** Stratégie Rancunier */
final class GrudgerStrategy implements Strategy {
    private boolean allowDefect;

    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        if (allowDefect) {
            return 0;
        } else {
            if (turnCount != 1 && otherPlayer.getLastChoice() == 0) {
                allowDefect = true;
                return 0;
            }
            return 1;
        }
    }
}
