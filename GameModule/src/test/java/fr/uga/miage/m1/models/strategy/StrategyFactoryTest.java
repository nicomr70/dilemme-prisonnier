package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The strategy factory")
class StrategyFactoryTest {
    @Test
    @DisplayName("should provide the 'random' strategy when type is null")
    void shouldProvideTheRandomStrategyWhenTypeIsNull() throws StrategyException {
        IStrategy strategy = StrategyFactory.getStrategyFromType(null);
        assertEquals(RandomStrategy.class, strategy.getClass());
    }
}