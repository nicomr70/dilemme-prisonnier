package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.RandomStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Pavlov aléatoire */
public final class RandomPavlovStrategy extends RandomStrategy {
    @Override
    public String getUniqueId() {
        return "RANDOM_PAVLOV";
    }

    @Override
    public String getFullName() {
        return "Pavlov aléatoire";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.COOPERATE;
        }
        int scoreDiff = data.getMainPlayerScore() - data.getMainPlayerPreviousScore();
        if (scoreDiff == 5 || scoreDiff == 3) {
            return chooseRandomly(data.getMainPlayerLastChoice());
        } else {
            return data.hasMainPlayerDefectedLastTurn() ? StrategyChoice.COOPERATE : StrategyChoice.DEFECT;
        }
    }
}
