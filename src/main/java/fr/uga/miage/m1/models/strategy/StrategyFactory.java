package fr.uga.miage.m1.models.strategy;

import java.lang.reflect.InvocationTargetException;

public class StrategyFactory {
    private StrategyFactory() {}

    public static IStrategy getStrategyFromType(StrategyType strategyType)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        if (strategyType == null) {
            return new RandomStrategy();
        }
        return strategyType.getStrategyClass().getDeclaredConstructor().newInstance();
    }
}
