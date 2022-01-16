package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Naive Prober' strategy")
class NaiveProberStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException, GameException {
        initGameWithAiPlayers("NAIVE_PROBER", "RANDOM");
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should generally copy the opponent's last choice with a small chance of being defective")
    void shouldCopyTheOpponentLastChoiceWithASmallChanceOfBeingDefective() throws StrategyException {
        iterateTurn(7);
        StrategyChoice playerLastChoice = player1.getLastChoice();
        assertTrue(playerLastChoice.isCooperate() || playerLastChoice.isDefect());
    }
}