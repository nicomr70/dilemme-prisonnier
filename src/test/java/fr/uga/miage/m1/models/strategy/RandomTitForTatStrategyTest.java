package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Random Tit for Tat' strategy")
class RandomTitForTatStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.RANDOM_TIT_FOR_TAT, StrategyType.RANDOM);
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should generally copy the opponent's last choice with a small chance of change")
    void shouldGenerallyCopyTheOpponentsLastChoiceWithASmallChanceOfChange() throws StrategyException {
        iterateTurn(3);
        PlayerChoice playerLastChoice = player1.getLastChoice();
        assertTrue(playerLastChoice.isCooperate() || playerLastChoice.isDefect());
    }
}