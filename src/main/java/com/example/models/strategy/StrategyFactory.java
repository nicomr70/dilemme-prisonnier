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
            case RANDOM_TIT_FOR_TAT:
                return new RandomTitForTatStrategy();
            case NAIVE_PROBER:
                return new NaiveProberStrategy();
            case GRIM_TRIGGER:
                return new GrimTriggerStrategy();
            case SOFT_GRUDGER:
                return new SoftGrudgerStrategy();
            case PAVLOV:
                return new PavlovStrategy();
            case RANDOM_PAVLOV:
                return new RandomPavlovStrategy();
            case RANDOM:
            default:
                return new RandomStrategy();
        }
    }
}
