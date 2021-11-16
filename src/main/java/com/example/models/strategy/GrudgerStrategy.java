package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

/** Stratégie Rancunier */
final class GrudgerStrategy implements IStrategy {
    private boolean allowDefect;

    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (allowDefect) {
            return PlayerChoice.DEFECT;
        } else {
            if (turnCount != 1 && otherPlayer.getLastChoice() == PlayerChoice.DEFECT) {
                allowDefect = true;
                return PlayerChoice.DEFECT;
            }
            return PlayerChoice.COOPERATE;
        }
    }
}
