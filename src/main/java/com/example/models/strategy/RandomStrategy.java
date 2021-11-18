package com.example.models.strategy;

import com.example.models.SingleRandom;
import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

import java.util.Random;

/** Stratégie Aléatoire */
class RandomStrategy implements IStrategy {
    private final double OPPOSITE_CHOICE_DEFAULT_PROBABILITY = 0.1;

    protected final Random random = SingleRandom.getInstance();

    public final PlayerChoice chooseRandomly(PlayerChoice defaultChoice, double oppositeChoiceProbability) {
        PlayerChoice oppositeChoice = defaultChoice.getOpposite();
        if (oppositeChoiceProbability <= 0.0 || oppositeChoiceProbability > 1.0) {
            oppositeChoiceProbability = OPPOSITE_CHOICE_DEFAULT_PROBABILITY;
        }
        if (random.nextDouble() < oppositeChoiceProbability) {
            return oppositeChoice;
        }
        return defaultChoice;
    }

    public final PlayerChoice chooseRandomly(PlayerChoice defaultChoice) {
        return chooseRandomly(defaultChoice, OPPOSITE_CHOICE_DEFAULT_PROBABILITY);
    }

    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        return chooseRandomly(PlayerChoice.COOPERATE, 0.5);
    }
}
