package com.example.models.strategy;

public enum StrategyType {
    COOPERATE("Toujours coopérer"),
    DEFECT("Toujours trahir"),
    RANDOM("Aléatoire"),
    TIT_FOR_TAT("Donnant-donnant"),
    SUSPICIOUS_TIT_FOR_TAT("Donnant-donnant soupçonneux"),
    GRUDGER("Rancunier"),
    PAVLOV("Pavlov");

    private String name;

    StrategyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
