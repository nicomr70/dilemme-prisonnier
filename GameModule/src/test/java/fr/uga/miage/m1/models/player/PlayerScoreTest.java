package fr.uga.miage.m1.models.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("A player score")
class PlayerScoreTest {
    PlayerScore score;

    @Test
    @DisplayName("should be initialized with zero values")
    void shouldBeInitializedWithZeroValues() {
        score = new PlayerScore();
        assertEquals(0, score.getPreviousValue());
        assertEquals(0, score.getCurrentValue());
    }

    @Nested
    @DisplayName("'addToValue' method")
    class AddToValueMethod {
        @BeforeEach
        void addToValue() {
            score = new PlayerScore();
            score.addToValue(19);
        }

        @Test
        @DisplayName("should save previous value")
        void shouldSavePreviousValue() {
            assertEquals(0, score.getPreviousValue());
            score.addToValue(42);
            assertEquals(19, score.getPreviousValue());
        }

        @Test
        @DisplayName("should update current value")
        void shouldUpdateCurrentValue() {
            assertEquals(19, score.getCurrentValue());
            score.addToValue(7);
            assertEquals(26, score.getCurrentValue());
        }
    }

    @Nested
    @DisplayName("'reset' method")
    class ResetMethod {
        @BeforeEach
        void reset() {
            score = new PlayerScore();
            score.addToValue(11);
            score.addToValue(13);
            score.reset();
        }

        @Test
        @DisplayName("should zero current value")
        void shouldZeroCurrentValue() {
            assertEquals(0, score.getCurrentValue());
        }

        @Test
        @DisplayName("should zero previous value")
        void shouldZeroPreviousValue() {
            assertEquals(0, score.getPreviousValue());
        }
    }

    @Nested
    @DisplayName("calculus")
    class Calculus {
        Player player1, player2;

        @BeforeEach
        void preparePlayers() {
            player1 = new Player("player1", null);
            player2 = new Player("player2", null);
        }

        @Nested
        @DisplayName("'applyCooperated' static method")
        class ApplyCooperatedStaticMethod {
            @BeforeEach
            void applyCooperated() {
                PlayerScore.applyCooperated(player1, player2);
            }

            @Test
            @DisplayName("should increase player 1 score by 3")
            void shouldIncreasePlayer1ScoreBy3() {
                assertEquals(3, player1.getScore());
            }

            @Test
            @DisplayName("should increase player 2 score by 3")
            void shouldIncreasePlayer2ScoreBy3() {
                assertEquals(3, player2.getScore());
            }
        }

        @Nested
        @DisplayName("'applyOneDefectedOther' static method")
        class ApplyOneDefectedOtherStaticMethod {
            @BeforeEach
            void applyOneDefectedOther() {
                PlayerScore.applyOneDefectedOther(player1, player2);
            }

            @Test
            @DisplayName("should increase player 1 score by 5")
            void shouldIncreasePlayer1ScoreBy5() {
                assertEquals(5, player1.getScore());
            }

            @Test
            @DisplayName("should not increase player 2 score")
            void shouldNotIncreasePlayer2Score() {
                assertEquals(0, player2.getScore());
            }
        }

        @Nested
        @DisplayName("'applyBothDefected' static method")
        class ApplyBothDefectedStaticMethod {
            @BeforeEach
            void applyBothDefected() {
                PlayerScore.applyBothDefected(player1, player2);
            }

            @Test
            @DisplayName("should increase player 1 score by 5")
            void shouldIncreasePlayer1ScoreBy1() {
                assertEquals(1, player1.getScore());
            }

            @Test
            @DisplayName("should increase player 2 score by 1")
            void shouldIncreasePlayer2ScoreBy1() {
                assertEquals(1, player2.getScore());
            }
        }
    }
}