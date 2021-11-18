package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/** Stratégie Rancunier */
final class SoftGrudgerStrategy implements IStrategy {
    private boolean allowPunishment;

    private final PlayerChoice[] punishment = {
            PlayerChoice.DEFECT,
            PlayerChoice.DEFECT,
            PlayerChoice.DEFECT,
            PlayerChoice.DEFECT,
            PlayerChoice.COOPERATE,
            PlayerChoice.COOPERATE
    };

    private final Supplier<Stream<PlayerChoice>> punishmentStreamSupplier = () -> Arrays.stream(punishment);

    private Iterator<PlayerChoice> punishmentIterator;

    private void resetPunishmentIterator() {
        punishmentIterator = punishmentStreamSupplier.get().iterator();
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
                resetPunishmentIterator();
                allowPunishment = false;
            }
            if (turnCount != 1 && otherPlayer.hasDefectedLastTurn()) {
                allowPunishment = true;
                return punishmentIterator.next();
            }
            return PlayerChoice.COOPERATE;
        }
    }
}
