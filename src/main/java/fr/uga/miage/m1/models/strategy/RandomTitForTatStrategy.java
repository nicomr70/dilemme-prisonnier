package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

/** Stratégie Donnant-donnant aléatoire */
final class RandomTitForTatStrategy extends RandomStrategy {
    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return PlayerChoice.COOPERATE;
        } else {
            return chooseRandomly(otherPlayer.getLastChoice());
        }
    }
}
