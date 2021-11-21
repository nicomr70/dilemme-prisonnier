package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'Gradual' strategy")
class GradualStrategyTest extends StrategyTest {
    @BeforeEach
    void initGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.GRADUAL, StrategyType.DEFECT);
    }

    @Test
    @DisplayName("should always cooperate at turn 1")
    void shouldAlwaysCooperateAtTurn1() throws StrategyException {
        turn();
        assertEquals(PlayerChoice.COOPERATE, player1.getLastChoice());
    }

    @Test
    @DisplayName("should not defect if the opponent cooperates")
    void shouldNotDefectIfTheOpponentCooperates() throws StrategyException {
        player2.setStrategy(StrategyFactory.getStrategyFromType(StrategyType.COOPERATE));
        iterateTurn(13);
        assertEquals(13, player1.getChoiceCount(PlayerChoice.COOPERATE));
        assertEquals(0, player1.getChoiceCount(PlayerChoice.DEFECT));
    }

    @Test
    @DisplayName("should punish the defecting opponent with the same amount of defections then cooperates two times")
    void shouldPunishTheDefectingOpponentWithTheSameAmountOfDefectionsThenCooperatesTwoTimes() throws StrategyException {
        iterateManualTurn(7, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.COOPERATE);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.COOPERATE);
        iterateManualTurn(11, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
        manualTurn(PlayerChoice.COOPERATE);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.DEFECT);
        assertWasLastPlayed(player1, PlayerChoice.DEFECT);
        manualTurn(PlayerChoice.COOPERATE);
        assertWasLastPlayed(player1, PlayerChoice.COOPERATE);
    }
}