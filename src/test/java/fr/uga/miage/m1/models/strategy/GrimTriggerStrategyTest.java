package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Grim Trigger' strategy")
class GrimTriggerStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.GRIM_TRIGGER, StrategyType.DEFECT);
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should defect from the moment the opponent has defected")
    void shouldDefectFromTheMomentTheOpponentHasDefected() throws StrategyException {
        iterateTurn(17);
        assertEquals(1, player1.getChoiceCount(PlayerChoice.COOPERATE));
        assertEquals(16, player1.getChoiceCount(PlayerChoice.DEFECT));
        initGameWithMixedPlayers(StrategyType.GRIM_TRIGGER);
        iterateManualTurn(11, PlayerChoice.COOPERATE);
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
        manualTurn(PlayerChoice.DEFECT);
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
        manualTurn(PlayerChoice.COOPERATE);
        assertEquals(PlayerChoice.DEFECT, player1.getLastChoice());
        manualTurn(PlayerChoice.COOPERATE);
        assertEquals(PlayerChoice.DEFECT, player1.getLastChoice());
    }

    @Test
    @DisplayName("should not defect if the opponent cooperates")
    void shouldNotDefectIfTheOpponentCooperates() throws StrategyException {
        player2.setStrategy(StrategyFactory.getStrategyFromType(StrategyType.COOPERATE));
        iterateTurn(19);
        assertEquals(19, player1.getChoiceCount(PlayerChoice.COOPERATE));
        assertEquals(0, player1.getChoiceCount(PlayerChoice.DEFECT));
    }
}