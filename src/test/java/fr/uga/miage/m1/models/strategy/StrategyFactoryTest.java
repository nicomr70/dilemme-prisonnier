package fr.uga.miage.m1.models.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The strategy factory")
class StrategyFactoryTest {
    @Test
    @DisplayName("should provide the 'random' strategy when type is null")
    void shouldProvideTheRandomStrategyWhenTypeIsNull()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        IStrategy strategy = StrategyFactory.getStrategyFromType(null);
        assertEquals(RandomStrategy.class, strategy.getClass());
    }

    @Test
    @DisplayName("should provide a strategy according to the input type")
    void shouldProvideAStrategyAccordingToTheInputType()
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException
    {
        for (StrategyType type : StrategyType.values()) {
            IStrategy strategy = StrategyFactory.getStrategyFromType(type);
            assertEquals(type.getStrategyClass(), strategy.getClass());
        }
    }
}