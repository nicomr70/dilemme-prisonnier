package fr.uga.miage.m1.models.strategy;

public class StrategyFactory {
    private StrategyFactory() {}

    public static IStrategy getStrategyFromType(StrategyType strategyType) {
        if (strategyType == null) {
            return new RandomStrategy();
        }
        return switch (strategyType) {
            case COOPERATE -> new CooperateStrategy();
            case DEFECT -> new DefectStrategy();
            case TIT_FOR_TAT -> new TitForTatStrategy();
            case SUSPICIOUS_TIT_FOR_TAT -> new SuspiciousTitForTatStrategy();
            case GENEROUS_TIT_FOR_TAT -> new GenerousTitForTatStrategy();
            case RANDOM_TIT_FOR_TAT -> new RandomTitForTatStrategy();
            case NAIVE_PROBER -> new NaiveProberStrategy();
            case GRIM_TRIGGER -> new GrimTriggerStrategy();
            case SOFT_GRUDGER -> new SoftGrudgerStrategy();
            case GRADUAL -> new GradualStrategy();
            case PAVLOV -> new PavlovStrategy();
            case RANDOM_PAVLOV -> new RandomPavlovStrategy();
            case RANDOM -> new RandomStrategy();
        };
    }
}
