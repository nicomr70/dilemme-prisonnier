package com.example.models.strategy;

import com.example.models.player.Player;
import com.example.models.player.PlayerChoice;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

/** Stratégie Gradual */
final class GradualStrategy implements IStrategy {
    private boolean allowPunishment;

    private List<PlayerChoice> punishment = new ArrayList<>();

    private Iterator<PlayerChoice> punishmentIterator;

    GradualStrategy() {
        resetPunishmentIterator();
    }

    private void calculatePunishment(int otherPlayerDefectCount) {
        List<PlayerChoice> punishmentEnd = List.of(PlayerChoice.COOPERATE, PlayerChoice.COOPERATE);
        punishment = new ArrayList<>(Collections.nCopies(otherPlayerDefectCount, PlayerChoice.DEFECT));
        punishment.addAll(punishmentEnd);
    }

    private void resetPunishmentIterator() {
        punishmentIterator = punishment.stream().iterator();
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
                calculatePunishment(otherPlayer.getChoiceCount(PlayerChoice.DEFECT));
                resetPunishmentIterator();
                allowPunishment = true;
                return punishmentIterator.next();
            }
            return PlayerChoice.COOPERATE;
        }
    }
}
