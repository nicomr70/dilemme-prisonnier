package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import lombok.AccessLevel;
import lombok.Getter;
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

    @Getter(AccessLevel.PUBLIC)
    private static final Map<String, String> STRATEGIES_FULL_NAME = new HashMap<>();
    @Getter(AccessLevel.PUBLIC)
    private static final Map<String, Class<? extends IStrategy>> STRATEGIES_MAP = getStrategiesMap();

    private StrategyFactory() {}

    private static Map<String, Class<? extends IStrategy>> getStrategiesMap() {
        Map<String, Class<? extends IStrategy>> strategiesMap = new HashMap<>();
        STRATEGIES_SET.forEach(strategyClass -> {
            try {
                IStrategy strategyId = strategyClass.getDeclaredConstructor().newInstance();
                strategiesMap.put(strategyId.getUniqueId(), strategyClass);
                STRATEGIES_FULL_NAME.put(strategyId.getFullName(),strategyId.getUniqueId());
            } catch (Exception e) {
                log.severe(e.getMessage());
            }
        });
        return strategiesMap;
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
