package com.example.models.player;

import com.example.models.SingleRandom;

public enum PlayerChoice {
    DEFECT,
    COOPERATE,
    NONE;

    public final PlayerChoice getOpposite() {
        if (this == NONE) {
            return SingleRandom.getInstance().nextBoolean() ? COOPERATE : DEFECT;
        }
        return this == COOPERATE ? DEFECT : COOPERATE;
    }

    public final boolean isDefect() {
        return this == DEFECT;
    }

    public final boolean isCooperate() {
        return this == COOPERATE;
    }

    public final boolean isNone() {
        return this == NONE;
    }
}
