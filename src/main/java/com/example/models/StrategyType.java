package com.example.models;

public enum StrategyType {
    COOPERATE("Toujours coopérer"),
    DEFECT("Toujours trahir"),
    RANDOM("Aléatoire"),
    TIT_FOR_TAT("Donnant-donnant"),
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
