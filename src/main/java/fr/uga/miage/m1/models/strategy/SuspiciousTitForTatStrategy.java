package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Donnant-donnant soupçonneux */
public final class SuspiciousTitForTatStrategy implements IStrategy {
    @Override
    public String getUniqueId() {
        return "SUSPICIOUS_TIT_FOR_TAT";
    }

    @Override
    public String getFullName() {
        return "Donnant-donnant suspicieux";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.DEFECT;
        } else {
            return data.getOpposingPlayerLastChoice();
        }
    }
}
