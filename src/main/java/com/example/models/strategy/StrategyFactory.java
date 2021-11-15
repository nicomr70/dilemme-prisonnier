package com.example.models.strategy;

public class StrategyFactory {
    public static Strategy getCooperateStrategy() {
        return new CooperateStrategy();
    }

    public static Strategy getDefectStrategy() {
        return new DefectStrategy();
    }

    public static Strategy getRandomStrategy() {
        return new RandomStrategy();
    }

    public static Strategy getTitForTatStrategy() {
        return new TitForTatStrategy();
    }

    public static Strategy getGrudgerStrategy() { return new GrudgerStrategy(); }

    public static Strategy getPavlovStrategy() {
        return new PavlovStrategy();
    }

    public static Strategy getStrategyFromType(StrategyType strategyType) {
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
