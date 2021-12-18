package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Rancunier */
public final class GrimTriggerStrategy implements IStrategy {
    private boolean allowDefect;

    @Override
    public String getUniqueId() {
        return "GRIM_TRIGGER";
    }

    @Override
    public String getFullName() {
        return "Rancunier";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (allowDefect) {
            return StrategyChoice.DEFECT;
        } else {
            if (data.getGameCurrentTurnCount() != 1 && data.hasOpposingPlayerDefectedLastTurn()) {
                allowDefect = true;
                return StrategyChoice.DEFECT;
            }
            return StrategyChoice.COOPERATE;
        }
    }
}
