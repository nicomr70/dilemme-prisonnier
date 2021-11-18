package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

/** Stratégie Rancunier */
final class SoftGrudgerStrategy implements IStrategy {
    private boolean allowPunishment;

    private final List<PlayerChoice> punishment = List.of(
            PlayerChoice.DEFECT,
            PlayerChoice.DEFECT,
            PlayerChoice.DEFECT,
            PlayerChoice.DEFECT,
            PlayerChoice.COOPERATE,
            PlayerChoice.COOPERATE
    );

    private Iterator<PlayerChoice> punishmentIterator;

    private void resetPunishmentIterator() {
        punishmentIterator = punishment.stream().iterator();
    }

    SoftGrudgerStrategy() {
        resetPunishmentIterator();
    }

    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (allowPunishment && punishmentIterator.hasNext()) {
            return punishmentIterator.next();
        } else {
            if (!punishmentIterator.hasNext()) {
                allowPunishment = false;
            }
            if (turnCount != 1 && otherPlayer.hasDefectedLastTurn()) {
                resetPunishmentIterator();
                allowPunishment = true;
                return punishmentIterator.next();
            }
            return PlayerChoice.COOPERATE;
        }
    }
}
