package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.GameException;
import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The 'Pavlov' strategy")
class PavlovStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException, GameException {
        initGameWithAiPlayers("PAVLOV", "RANDOM");
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should repeat last choice when 3 or 5 points were earned")
    void shouldRepeatLastChoiceWhen3Or5PointsWereEarned() throws StrategyException {
        iterateManualTurn(2, StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
    }
}