package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

/** Stratégie Donnant-donnant aléatoire */
final class RandomTitForTatStrategy extends RandomStrategy {
    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return PlayerChoice.COOPERATE;
        } else {
            return chooseRandomly(otherPlayer.getLastChoice());
        }
    }
}
