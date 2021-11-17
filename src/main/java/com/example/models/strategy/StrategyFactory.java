package com.example.models.strategy;

public class StrategyFactory {
    public static IStrategy getStrategyFromType(StrategyType strategyType) {
        switch (strategyType) {
            case COOPERATE:
                return new CooperateStrategy();
            case DEFECT:
                return new DefectStrategy();
            case TIT_FOR_TAT:
                return new TitForTatStrategy();
            case SUSPICIOUS_TIT_FOR_TAT:
                return new SuspiciousTitForTatStrategy();
            case GENEROUS_TIT_FOR_TAT:
                return new GenerousTitForTatStrategy();
            case GRIM_TRIGGER:
                return new GrimTriggerStrategy();
            case PAVLOV:
                return new PavlovStrategy();
            case RANDOM:
            default:
                return new RandomStrategy();
        }
    }
}
