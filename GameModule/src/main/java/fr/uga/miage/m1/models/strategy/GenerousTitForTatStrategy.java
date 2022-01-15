package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Donnant-donnant */
public final class GenerousTitForTatStrategy extends RandomStrategy {
    @Override
    public String getUniqueId() {
        return "GENEROUS_TIT_FOR_TAT";
    }

    @Override
    public String getFullName() {
        return "Pacificateur naïf";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.COOPERATE;
        } else {
            return data.hasOpposingPlayerDefectedLastTurn() ? chooseRandomly(StrategyChoice.DEFECT) : StrategyChoice.COOPERATE;
        }
    }
}
