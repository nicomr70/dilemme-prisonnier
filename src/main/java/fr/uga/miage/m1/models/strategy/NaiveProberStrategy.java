package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

/** Stratégie Sondeur naïf */
final class NaiveProberStrategy extends RandomStrategy {
    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return PlayerChoice.COOPERATE;
        } else {
            return otherPlayer.hasCooperatedLastTurn() ? chooseRandomly(PlayerChoice.COOPERATE) : PlayerChoice.DEFECT;
        }
    }
}
