package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import lombok.extern.java.Log;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

@Log
public final class StrategyFactory {
    private static final Set<Class<? extends IStrategy>> STRATEGIES_SET =
            new Reflections("fr.uga.miage.m1")
                    .getSubTypesOf(IStrategy.class);

    public static final Map<String, Class<? extends IStrategy>> STRATEGIES_MAP = getStrategiesMap();

    private StrategyFactory() {}

    private static Map<String, Class<? extends IStrategy>> getStrategiesMap() {
        return null;
    }

    public static IStrategy getStrategyFromType(String strategyId) throws StrategyException {
        if (strategyId == null || strategyId.equals("")) {
            return new RandomStrategy();
        }
        try {
            return STRATEGIES_MAP.get(strategyId).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new StrategyException("could not be generated from type " + strategyId, e);
        }
    }
}
