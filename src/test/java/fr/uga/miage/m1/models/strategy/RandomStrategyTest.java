package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@DisplayName("A 'Random' strategy")
class RandomStrategyTest extends StrategyTest {
    RandomStrategy randomStrategy;

    @Nested
    @DisplayName("'chooseRandomly' method")
    class ChooseRandomlyMethod {
        PlayerChoice mainChoice = PlayerChoice.COOPERATE;

        @BeforeEach
        void prepareStrategy() throws StrategyException {
            randomStrategy = (RandomStrategy) StrategyFactory.getStrategyFromType(StrategyType.RANDOM);
        }

        @Test
        @DisplayName("should return main choice when opposite choice probability is 0%")
        void shouldReturnMainChoiceWhenOppositeChoiceProbabilityIsO() {
            assertEquals(mainChoice, randomStrategy.chooseRandomly(mainChoice, 0.0));
        }

        @Test
        @DisplayName("should return opposite choice when its probability is 100%")
        void shouldReturnOppositeChoiceWhenItsProbabilityIs100() {
            assertEquals(mainChoice.getOpposite(), randomStrategy.chooseRandomly(mainChoice, 1.0));
        }

        @Test
        @DisplayName("should return a choice by default when opposite choice probability is wrong")
        void shouldReturnAChoiceByDefaultWhenOppositeChoiceProbabilityIsWrong() {
            assertNotNull(randomStrategy.chooseRandomly(mainChoice, -1.0));
            assertNotNull(randomStrategy.chooseRandomly(mainChoice, 2.0));
        }

        @Test
        @DisplayName("should not return a NONE choice")
        void shouldNotReturnANoneChoice() {
            assertNotEquals(PlayerChoice.NONE, randomStrategy.chooseRandomly(mainChoice));
        }
    }

    @Test
    @DisplayName("should cooperate or defect evenly")
    void shouldCooperateOrDefectEvenly() throws StrategyException {
        randomStrategy = (RandomStrategy) StrategyFactory.getStrategyFromType(StrategyType.RANDOM);
        PlayerChoice resultChoice = randomStrategy.execute(3, mock(Player.class), mock(Player.class));
        assertTrue(resultChoice.isCooperate() || resultChoice.isDefect());
    }
}