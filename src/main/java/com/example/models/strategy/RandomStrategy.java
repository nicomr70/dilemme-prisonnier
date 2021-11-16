package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

import java.util.Random;

/** Stratégie Aléatoire */
class RandomStrategy implements IStrategy {
    protected final Random random = new Random();

    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        return random.nextBoolean() ? PlayerChoice.COOPERATE : PlayerChoice.DEFECT;
    }
}
