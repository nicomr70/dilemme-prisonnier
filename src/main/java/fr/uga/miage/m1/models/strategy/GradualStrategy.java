package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

import java.util.*;

/** Stratégie Gradual */
final class GradualStrategy implements IStrategy {
    private boolean allowPunishment;

    private List<StrategyChoice> punishment = new ArrayList<>();

    private Iterator<StrategyChoice> punishmentIterator;

    GradualStrategy() {
        resetPunishmentIterator();
    }

    private void calculatePunishment(StrategyExecutionData data) {
        int opposingPlayerDefectCount = data.getOpposingPlayerChoiceCount(StrategyChoice.DEFECT);
        List<StrategyChoice> punishmentEnd = List.of(StrategyChoice.COOPERATE, StrategyChoice.COOPERATE);
        punishment = new ArrayList<>(Collections.nCopies(opposingPlayerDefectCount, StrategyChoice.DEFECT));
        punishment.addAll(punishmentEnd);
    }

    private void resetPunishmentIterator() {
        punishmentIterator = punishment.stream().iterator();
    }

    @Override
    public String getUniqueId() {
        return "GRADUAL";
    }

    @Override
    public String getFullName() {
        return "Graduel";
    }

    @Override
    public StrategyChoice execute(StrategyExecutionData data) {
        if (allowPunishment && punishmentIterator.hasNext()) {
            return punishmentIterator.next();
        } else {
            if (!punishmentIterator.hasNext()) {
                allowPunishment = false;
            }
            if (data.getGameCurrentTurnCount() != 1 && data.hasOpposingPlayerDefectedLastTurn()) {
                calculatePunishment(data);
                resetPunishmentIterator();
                allowPunishment = true;
                return punishmentIterator.next();
            }
            return StrategyChoice.COOPERATE;
        }
    }
}
