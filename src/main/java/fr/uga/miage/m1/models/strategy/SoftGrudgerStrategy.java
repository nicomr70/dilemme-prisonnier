package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;

import java.util.Iterator;
import java.util.List;

/** Stratégie Rancunier */
public final class SoftGrudgerStrategy implements IStrategy {
    private boolean allowPunishment;

    private final List<StrategyChoice> punishment = List.of(
            StrategyChoice.DEFECT,
            StrategyChoice.DEFECT,
            StrategyChoice.DEFECT,
            StrategyChoice.DEFECT,
            StrategyChoice.COOPERATE,
            StrategyChoice.COOPERATE
    );

    private Iterator<StrategyChoice> punishmentIterator;

    private void resetPunishmentIterator() {
        punishmentIterator = punishment.stream().iterator();
    }

    public SoftGrudgerStrategy() {
        resetPunishmentIterator();
    }

    @Override
    public String getUniqueId() {
        return "SOFT_GRUDGER";
    }

    @Override
    public String getFullName() {
        return "Rancunier doux";
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
                resetPunishmentIterator();
                allowPunishment = true;
                return punishmentIterator.next();
            }
            return StrategyChoice.COOPERATE;
        }
    }
}
