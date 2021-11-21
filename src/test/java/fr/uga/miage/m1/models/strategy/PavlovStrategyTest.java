package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Pavlov' strategy")
class PavlovStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.PAVLOV, StrategyType.RANDOM);
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should repeat last choice when 3 or 5 points were earned")
    void shouldRepeatLastChoiceWhen3Or5PointsWereEarned() throws StrategyException {
        iterateManualTurn(2, PlayerChoice.COOPERATE);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.COOPERATE);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.COOPERATE);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
    }
}