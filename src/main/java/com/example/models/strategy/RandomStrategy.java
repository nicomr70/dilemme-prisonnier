package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

import java.util.Random;

/** Stratégie Aléatoire */
final class RandomStrategy implements IStrategy {
    private final Random rand = new Random();

    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        return rand.nextBoolean() ? PlayerChoice.COOPERATE : PlayerChoice.DEFECT;
    }
}
