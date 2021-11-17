package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

/** Stratégie Pavlov */
final class PavlovStrategy implements IStrategy {
    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return PlayerChoice.COOPERATE;
        }
        int scoreDiff = player.getScore() - player.getPreviousScore();
        if (scoreDiff == 5 || scoreDiff == 3) {
            return player.getLastChoice();
        } else {
            return  player.hasDefectedLastTurn() ? PlayerChoice.COOPERATE : PlayerChoice.DEFECT;
        }
    }
}
