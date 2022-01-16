package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The 'Suspicious Tit for Tat' strategy")
class SuspiciousTitForTatStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException, GameException {
        initGameWithAiPlayers("SUSPICIOUS_TIT_FOR_TAT", "RANDOM");
    }

    @Test
    @DisplayName("should always defect at turn 1")
    void shouldAlwaysDefectAtTurn1() throws StrategyException {
        turn();
        assertEquals(StrategyChoice.DEFECT, player1.getLastChoice());
    }

    @Test
    @DisplayName("should copy the opponent's last choice")
    void shouldCopyTheOpponentLastChoice() throws StrategyException {
        iterateTurn(11);
        StrategyChoice opponentLastChoice = player2.getLastChoice();
        turn();
        assertEquals(opponentLastChoice, player1.getLastChoice());
    }
}