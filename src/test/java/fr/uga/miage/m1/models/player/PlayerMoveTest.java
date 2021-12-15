package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A player move")
class PlayerMoveTest {
    final Player player = new Player("player", null);
    final StrategyChoice choice = StrategyChoice.COOPERATE;
    final int turnNumber = 42;
    PlayerMove move = new PlayerMove(player, choice, turnNumber);

    @Test
    @DisplayName("'player' getter should return a player")
    void playerGetterShouldReturnAPlayer() {
        assertEquals(player, move.getPlayer());
    }

    @Test
    @DisplayName("'choice' getter should return a choice")
    void choiceGetterShouldReturnAChoice() {
        assertEquals(choice, move.getChoice());
    }

    @Test
    @DisplayName("'turn number' getter should return an integer")
    void turnNumberGetterShouldReturnAnInteger() {
        assertEquals(42, move.getTurnNumber());
    }
}