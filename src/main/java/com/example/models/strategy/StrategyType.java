package com.example.models.strategy;

public enum StrategyType {
    COOPERATE("Toujours coopérer"),
    DEFECT("Toujours trahir"),
    RANDOM("Aléatoire"),
    TIT_FOR_TAT("Donnant-donnant"),
    SUSPICIOUS_TIT_FOR_TAT("Donnant-donnant soupçonneux"),
    RANDOM_TIT_FOR_TAT("Donnant-donnant aléatoire"),
    GENEROUS_TIT_FOR_TAT("Pacificateur naïf"),
    NAIVE_PROBER("Sondeur naïf"),
    GRIM_TRIGGER("Rancunier"),
    SOFT_GRUDGER("Rancunier doux"),
    GRADUAL("Graduel"),
    PAVLOV("Pavlov"),
    RANDOM_PAVLOV("Pavlov aléatoire");

    private String name;

    StrategyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
