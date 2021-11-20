package fr.uga.miage.m1.models.strategy;

public enum StrategyType {
    COOPERATE("Toujours coopérer", CooperateStrategy.class),
    DEFECT("Toujours trahir", DefectStrategy.class),
    RANDOM("Aléatoire", RandomStrategy.class),
    TIT_FOR_TAT("Donnant-donnant", TitForTatStrategy.class),
    SUSPICIOUS_TIT_FOR_TAT("Donnant-donnant soupçonneux", SuspiciousTitForTatStrategy.class),
    RANDOM_TIT_FOR_TAT("Donnant-donnant aléatoire", RandomTitForTatStrategy.class),
    GENEROUS_TIT_FOR_TAT("Pacificateur naïf", GenerousTitForTatStrategy.class),
    NAIVE_PROBER("Sondeur naïf", NaiveProberStrategy.class),
    GRIM_TRIGGER("Rancunier", GrimTriggerStrategy.class),
    SOFT_GRUDGER("Rancunier doux", SoftGrudgerStrategy.class),
    GRADUAL("Graduel", GradualStrategy.class),
    PAVLOV("Pavlov", PavlovStrategy.class),
    RANDOM_PAVLOV("Pavlov aléatoire", RandomPavlovStrategy.class);

    private final String name;
    private final Class<? extends IStrategy> strategyClass;

    StrategyType(String name, Class<? extends IStrategy> strategyClass) {
        this.name = name;
        this.strategyClass = strategyClass;
    }

    public String getName() {
        return name;
    }

    public Class<? extends IStrategy> getStrategyClass() {
        return strategyClass;
    }
}
