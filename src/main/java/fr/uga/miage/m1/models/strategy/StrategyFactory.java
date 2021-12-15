package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import lombok.extern.java.Log;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Log
public final class StrategyFactory {
    private static final Set<Class<? extends IStrategy>> STRATEGIES_SET =
            new Reflections("fr.uga.miage.m1")
                    .getSubTypesOf(IStrategy.class);

    public static final Map<String, Class<? extends IStrategy>> STRATEGIES = new HashMap<>();

    private StrategyFactory() throws StrategyException {
        for (Class<? extends IStrategy> strategyClass : STRATEGIES_SET) {
            try {
                IStrategy strategy = strategyClass.getDeclaredConstructor().newInstance();
                STRATEGIES.put(strategy.getUniqueId(), strategy.getClass());
            } catch (Exception e) {
                throw new StrategyException("could not initialize", e);
            }
        }

    }

    public static IStrategy getStrategyFromType(String strategyId) throws StrategyException {
        if (strategyId == null || strategyId.equals("")) {
            return new RandomStrategy();
        }
        try {
            return STRATEGIES.get(strategyId).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new StrategyException("could not be generated from type " + strategyId, e);
        }
    }
}
