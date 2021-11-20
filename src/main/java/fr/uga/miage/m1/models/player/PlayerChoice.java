package fr.uga.miage.m1.models.player;

public enum PlayerChoice {
    DEFECT,
    COOPERATE,
    NONE;

    public final PlayerChoice getOpposite() {
        if (this == NONE) {
            return NONE;
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
