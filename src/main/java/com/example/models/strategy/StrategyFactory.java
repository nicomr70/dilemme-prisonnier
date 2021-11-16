package com.example.models.strategy;

public class StrategyFactory {
    public static IStrategy getCooperateStrategy() {
        return new CooperateStrategy();
    }

    public static IStrategy getDefectStrategy() {
        return new DefectStrategy();
    }

    public static IStrategy getRandomStrategy() {
        return new RandomStrategy();
    }

    public static IStrategy getTitForTatStrategy() {
        return new TitForTatStrategy();
    }

    public static IStrategy getGrudgerStrategy() { return new GrudgerStrategy(); }

    public static IStrategy getPavlovStrategy() {
        return new PavlovStrategy();
    }

    public static IStrategy getStrategyFromType(StrategyType strategyType) {
        switch (strategyType) {
            case COOPERATE:
                return new CooperateStrategy();
            case DEFECT:
                return new DefectStrategy();
            case TIT_FOR_TAT:
                return new TitForTatStrategy();
            case GRUDGER:
                return new GrudgerStrategy();
            case PAVLOV:
                return new PavlovStrategy();
            case RANDOM:
            default:
                return new RandomStrategy();
        }
    }
}
