package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The 'Tit for Tat' strategy")
class TitForTatStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers("TIT_FOR_TAT", "RANDOM");
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should copy the opponent's last choice")
    void shouldCopyTheOpponentLastChoice() throws StrategyException {
        iterateTurn(7);
        StrategyChoice opponentLastChoice = player2.getLastChoice();
        turn();
        assertEquals(opponentLastChoice, player1.getLastChoice());
    }
}