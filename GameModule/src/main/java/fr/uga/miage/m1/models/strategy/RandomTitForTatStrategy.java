package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Donnant-donnant aléatoire */
public final class RandomTitForTatStrategy extends RandomStrategy {
    @Override
    public String getUniqueId() {
        return "RANDOM_TIT_FOR_TAT";
    }

    @Override
    public String getFullName() {
        return "Donnant-donnant aléatoire";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.COOPERATE;
        } else {
            return chooseRandomly(data.getOpposingPlayerLastChoice());
        }
    }
}
