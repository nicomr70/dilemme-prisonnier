package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SuspiciousTitForTatStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.SUSPICIOUS_TIT_FOR_TAT, StrategyType.RANDOM);
        game = new Game(DEFAULT_MAX_TURN_COUNT, player1, player2);
    }

    @Test
    @DisplayName("should always defect at turn 1")
    void shouldAlwaysDefectAtTurn1() throws StrategyException {
        turn();
        assertEquals(PlayerChoice.DEFECT, player1.getLastChoice());
    }

    @Test
    @DisplayName("should copy the opponent's last choice")
    void shouldCopyTheOpponentLastChoice() throws StrategyException {
        iterateTurn(11);
        PlayerChoice opponentLastChoice = player2.getLastChoice();
        turn();
        assertEquals(opponentLastChoice, player1.getLastChoice());
    }
}