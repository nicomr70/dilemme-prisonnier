package com.example.models.strategies;

import com.example.models.Player;
import com.example.models.Strategy;

/** Stratégie Pavlov */
public class PavlovStrategy implements Strategy {
    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return 1;
        }
        int scoreDiff = player.getScore() - player.getPreviousScore();
        byte playerLastChoice = player.getLastChoice();
        if (scoreDiff == 5 || scoreDiff == 3) {
            return playerLastChoice;
        } else {
            return (byte) (playerLastChoice == 0 ? 1 : 0);
        }
    }
}
