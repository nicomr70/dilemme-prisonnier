package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Donnant-donnant */
public final class TitForTatStrategy implements IStrategy {
    @Override
    public String getUniqueId() {
        return "TIT_FOR_TAT";
    }

    @Override
    public String getFullName() {
        return "Donnant-donnant";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.COOPERATE;
        } else {
            return data.getOpposingPlayerLastChoice();
        }
    }
}
