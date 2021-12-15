package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

/** Stratégie Pavlov */
final class PavlovStrategy implements IStrategy {
    @Override
    public String getUniqueId() {
        return "PAVLOV";
    }

    @Override
    public String getFullName() {
        return "Pavlov";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (data.getGameCurrentTurnCount() == 1) {
            return StrategyChoice.COOPERATE;
        }
        int scoreDiff = data.getMainPlayerScore() - data.getMainPlayerPreviousScore();
        if (scoreDiff == 5 || scoreDiff == 3) {
            return data.getMainPlayerLastChoice();
        } else {
            return  data.hasMainPlayerDefectedLastTurn() ? StrategyChoice.COOPERATE : StrategyChoice.DEFECT;
        }
    }
}
