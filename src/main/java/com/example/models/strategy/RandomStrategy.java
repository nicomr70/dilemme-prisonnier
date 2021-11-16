package com.example.models.strategy;

import com.example.models.Player;

import java.util.Random;

/** Stratégie Aléatoire */
final class RandomStrategy implements IStrategy {
    private final Random rand = new Random();

    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return (byte) (rand.nextBoolean() ? 1 : 0);
    }
}
