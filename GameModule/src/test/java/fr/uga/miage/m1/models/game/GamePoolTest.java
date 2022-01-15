package fr.uga.miage.m1.models.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@DisplayName("A game pool")
class GamePoolTest {
    GamePool gamePool;

    @BeforeEach
    void setup() {
        gamePool = new GamePool();
    }

    @Test
    @DisplayName("'registerGame' method should save the inputted game")
    void registerGameMethodShouldSaveTheInputtedGame() {
        Game game = mock(Game.class);
        gamePool.registerGame(game);
        assertTrue(gamePool.asCollection().contains(game));
    }

    @Test
    @DisplayName("'getGame' method should return the game with the inputted id")
    void getGameMethodShouldReturnTheGameWithTheInputtedId() {
        Game game = mock(Game.class);
        gamePool.registerGame(game);
        assertEquals(game, gamePool.getGame(game.getId()));
    }
}