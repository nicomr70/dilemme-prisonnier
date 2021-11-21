package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;

import java.lang.reflect.InvocationTargetException;

public class StrategyFactory {
    private StrategyFactory() {}

    public static IStrategy getStrategyFromType(StrategyType strategyType) throws StrategyException {
        if (strategyType == null) {
            return new RandomStrategy();
        }
        try {
            return strategyType.getStrategyClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new StrategyException("could not be generated from type", e);
        }
    }
}
