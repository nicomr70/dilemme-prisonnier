package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Grim Trigger' strategy")
class GrimTriggerStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers("GRIM_TRIGGER", "DEFECT");
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should defect from the moment the opponent has defected")
    void shouldDefectFromTheMomentTheOpponentHasDefected() throws StrategyException {
        iterateTurn(17);
        assertEquals(1, player1.getChoiceCount(StrategyChoice.COOPERATE));
        assertEquals(16, player1.getChoiceCount(StrategyChoice.DEFECT));
        initGameWithMixedPlayers("GRIM_TRIGGER");
        iterateManualTurn(11, StrategyChoice.COOPERATE);
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
        manualTurn(StrategyChoice.DEFECT);
        assertEquals(StrategyChoice.COOPERATE, player1.getLastChoice());
        manualTurn(StrategyChoice.COOPERATE);
        assertEquals(StrategyChoice.DEFECT, player1.getLastChoice());
        manualTurn(StrategyChoice.COOPERATE);
        assertEquals(StrategyChoice.DEFECT, player1.getLastChoice());
    }

    @Test
    @DisplayName("should not defect if the opponent cooperates")
    void shouldNotDefectIfTheOpponentCooperates() throws StrategyException {
        player2.setStrategy(StrategyFactory.getStrategyFromType("COOPERATE"));
        iterateTurn(19);
        assertEquals(19, player1.getChoiceCount(StrategyChoice.COOPERATE));
        assertEquals(0, player1.getChoiceCount(StrategyChoice.DEFECT));
    }
}