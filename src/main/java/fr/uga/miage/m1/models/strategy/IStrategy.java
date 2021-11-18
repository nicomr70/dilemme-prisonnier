package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

public interface IStrategy {
    PlayerChoice execute(int turnCount, Player player, Player otherPlayer);
}
