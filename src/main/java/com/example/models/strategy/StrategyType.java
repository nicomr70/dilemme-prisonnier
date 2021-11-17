package com.example.models.strategy;

public enum StrategyType {
    COOPERATE("Toujours coopérer"),
    DEFECT("Toujours trahir"),
    RANDOM("Aléatoire"),
    TIT_FOR_TAT("Donnant-donnant"),
    SUSPICIOUS_TIT_FOR_TAT("Donnant-donnant soupçonneux"),
    GENEROUS_TIT_FOR_TAT("Pacificateur naïf"),
    GRIM_TRIGGER("Rancunier"),
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
