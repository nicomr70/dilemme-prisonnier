package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.models.SingleRandom;

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

    public final boolean is(PlayerChoice choice) {
        return this == choice;
    }
}
