package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Soft Grudger' strategy")
class SoftGrudgerStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers("SOFT_GRUDGER", "DEFECT");
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should not defect if the opponent cooperates")
    void shouldNotDefectIfTheOpponentCooperates() throws StrategyException {
        player2.setStrategy(StrategyFactory.getStrategyFromType("COOPERATE"));
        iterateTurn(5);
        assertEquals(5, player1.getChoiceCount(StrategyChoice.COOPERATE));
        assertEquals(0, player1.getChoiceCount(StrategyChoice.DEFECT));
    }

    @Test
    @DisplayName("should punish the defecting opponent with 4 defections then cooperates two times")
    void shouldPunishTheDefectingOpponentWith4DefectionsThenCooperatesTwoTimes() throws StrategyException {
        iterateManualTurn(3, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        iterateManualTurn(7, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.DEFECT);
        manualTurn(StrategyChoice.DEFECT);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
        manualTurn(StrategyChoice.COOPERATE);
        assertWasLastPlayed(player1, StrategyChoice.COOPERATE);
    }
}