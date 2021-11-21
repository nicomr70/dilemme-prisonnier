package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Generous Tit for Tat' strategy")
class GenerousTitForTatStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.GENEROUS_TIT_FOR_TAT, StrategyType.RANDOM);
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should generally copy the opponent's last choice")
    void shouldCopyTheOpponentLastChoice() throws StrategyException {
        iterateTurn(7);
        PlayerChoice playerLastChoice = player1.getLastChoice();
        assertTrue(playerLastChoice.isCooperate() || playerLastChoice.isDefect());
    }
}