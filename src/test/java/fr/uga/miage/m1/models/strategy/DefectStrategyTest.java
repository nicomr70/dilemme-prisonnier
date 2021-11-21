package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The 'Defect' strategy")
class DefectStrategyTest extends StrategyTest {
    @BeforeEach
    void prepareGame() throws StrategyException {
        initGameWithAiPlayers(StrategyType.DEFECT, StrategyType.RANDOM);
        game = new Game(DEFAULT_MAX_TURN_COUNT, player1, player2);
    }

    @Test
    @DisplayName("should always defect")
    void shouldAlwaysDefect() throws StrategyException {
        iterateTurn(DEFAULT_MAX_TURN_COUNT);
        assertEquals(DEFAULT_MAX_TURN_COUNT, player1.getChoiceCount(PlayerChoice.DEFECT));
    }
}