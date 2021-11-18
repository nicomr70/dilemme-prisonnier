package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

/** Stratégie Rancunier */
final class GrimTriggerStrategy implements IStrategy {
    private boolean allowDefect;

    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (allowDefect) {
            return PlayerChoice.DEFECT;
        } else {
            if (turnCount != 1 && otherPlayer.hasDefectedLastTurn()) {
                allowDefect = true;
                return PlayerChoice.DEFECT;
            }
            return PlayerChoice.COOPERATE;
        }
    }
}
