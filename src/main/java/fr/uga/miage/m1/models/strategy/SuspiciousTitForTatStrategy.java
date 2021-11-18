package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

/** Stratégie Donnant-donnant soupçonneux */
final class SuspiciousTitForTatStrategy implements IStrategy {
    @Override
    public PlayerChoice execute(int turnCount, Player player, Player otherPlayer) {
        if (turnCount == 1) {
            return PlayerChoice.DEFECT;
        } else {
            return otherPlayer.getLastChoice();
        }
    }
}
