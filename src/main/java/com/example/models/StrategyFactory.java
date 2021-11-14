package com.example.models;

import com.example.models.strategies.*;

public final class StrategyFactory {
    public Strategy getCooperateStrategy() {
        return new CooperateStrategy();
    }

    public Strategy getDefectStrategy() {
        return new DefectStrategy();
    }

    public Strategy getRandomStrategy() {
        return new RandomStrategy();
    }

    public Strategy getTitForTatStrategy() {
        return new TitForTatStrategy();
    }

    public Strategy getGrudgerStrategy() { return new GrudgerStrategy();
    }

    public Strategy getPavlovStrategy() {
        return new PavlovStrategy();
    }

    public Strategy getStrategyFromType(StrategyType strategyType) {
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
