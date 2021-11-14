package com.example.models.strategies;

import com.example.models.Player;
import com.example.models.Strategy;

import java.util.Random;

/** Stratégie Aléatoire */
public final class RandomStrategy implements Strategy {
    private final Random rand = new Random();

    @Override
    public byte execute(int turnCount, Player player, Player otherPlayer) {
        return (byte) (rand.nextBoolean() ? 1 : 0);
    }
}
