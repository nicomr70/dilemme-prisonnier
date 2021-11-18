package fr.uga.miage.m1.models.player;

public class PlayerScore {
    private int currentValue;
    private int previousValue;

    public final static int SCORE_DEFECTED = 0;
    public final static int SCORE_DEFECT_ONE = 5;
    public final static int SCORE_DEFECT_BOTH = 1;
    public final static int SCORE_COOPERATE = 3;

    public int getCurrentValue() {
        return currentValue;
    }

    public int getPreviousValue() {
        return previousValue;
    }

    public void addToValue(int valueToAdd) {
        previousValue = currentValue;
        currentValue += valueToAdd;
    }

    public void reset() {
        currentValue = 0;
        previousValue = 0;
    }

    public static void applyCooperated(Player player1, Player player2) {
        player1.increaseScore(SCORE_COOPERATE);
        player2.increaseScore(SCORE_COOPERATE);
    }

    public static void applyOneDefectedOther(Player player1, Player player2) {
        player1.increaseScore(SCORE_DEFECT_ONE);
        player2.increaseScore(SCORE_DEFECTED);
    }

    public static void applyBothDefected(Player player1, Player player2) {
        player1.increaseScore(SCORE_DEFECT_BOTH);
        player2.increaseScore(SCORE_DEFECT_BOTH);
    }
}
