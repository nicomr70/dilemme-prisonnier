package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

/** Stratégie Donnant-donnant soupçonneux */
final class SuspiciousTitForTatStrategy implements IStrategy {
    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return PlayerChoice.DEFECT;
        } else {
            return otherPlayer.getLastChoice();
        }
    }
}
