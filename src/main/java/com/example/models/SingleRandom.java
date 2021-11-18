package com.example.models;

import java.util.Random;

public final class SingleRandom extends Random {
    private static SingleRandom instance = null;

    private SingleRandom() {
        super();
    }

    public static SingleRandom getInstance() {
        if (instance == null) {
            instance = new SingleRandom();
        }
        return instance;
    }
}
