package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("The 'Cooperate' strategy")
class CooperateStrategyTest extends StrategyTest {
    @BeforeEach
    void prepareGame() throws StrategyException {
        initAiPlayers(StrategyType.COOPERATE, StrategyType.RANDOM);
        game = new Game(DEFAULT_MAX_TURN_COUNT, player1, player2);
    }

    @Test
    @DisplayName("should always cooperate")
    void shouldAlwaysCooperate() throws StrategyException {
        iterateTurn(DEFAULT_MAX_TURN_COUNT);
        assertEquals(DEFAULT_MAX_TURN_COUNT, player1.getChoiceCount(PlayerChoice.COOPERATE));
    }
}
