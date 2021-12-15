package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Sondeur naïf */
final class NaiveProberStrategy extends RandomStrategy {
    @Override
    public String getUniqueId() {
        return "NAIVE_PROBER";
    }

    @Override
    public String getFullName() {
        return "Sondeur naïf";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.COOPERATE;
        } else {
            return data.hasOpposingPlayerCooperatedLastTurn() ? chooseRandomly(StrategyChoice.COOPERATE) : StrategyChoice.DEFECT;
        }
    }
}
