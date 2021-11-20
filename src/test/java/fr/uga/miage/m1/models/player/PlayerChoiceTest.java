package fr.uga.miage.m1.models.player;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A player choice")
class PlayerChoiceTest {
    PlayerChoice choice;

    @Nested
    @DisplayName("'getOpposite' method")
    class GetOppositeMethod {
        @Test
        @DisplayName("should return NONE when choice is NONE")
        void shouldReturnNoneWhenChoiceIsNone() {
            choice = PlayerChoice.NONE;
            assertEquals(PlayerChoice.NONE, choice.getOpposite());
        }

        @Test
        @DisplayName("should return DEFECT when choice is COOPERATE")
        void shouldReturnDefectWhenChoiceIsCooperate() {
            choice = PlayerChoice.COOPERATE;
            assertEquals(PlayerChoice.DEFECT, choice.getOpposite());
        }

        @Test
        @DisplayName("should return COOPERATE when choice is DEFECT")
        void shouldReturnCooperateWhenChoiceIsDefect() {
            choice = PlayerChoice.DEFECT;
            assertEquals(PlayerChoice.COOPERATE, choice.getOpposite());
        }
    }

    @Nested
    @DisplayName("'isDefect' method")
    class IsDefectMethod {
        @Test
        @DisplayName("should return true when choice is DEFECT")
        void shouldReturnTrueWhenChoiceIsDefect() {
            choice = PlayerChoice.DEFECT;
            assertTrue(choice.isDefect());
        }

        @Test
        @DisplayName("should return false when choice is COOPERATE")
        void shouldReturnFalseWhenChoiceIsCooperate() {
            choice = PlayerChoice.COOPERATE;
            assertFalse(choice.isDefect());
        }

        @Test
        @DisplayName("should return false when choice is NONE")
        void shouldReturnFalseWhenChoiceIsNone() {
            choice = PlayerChoice.NONE;
            assertFalse(choice.isDefect());
        }
    }

    @Nested
    @DisplayName("'isCooperate' method")
    class IsCooperateMethod {
        @Test
        @DisplayName("should return false when choice is DEFECT")
        void shouldReturnFalseWhenChoiceIsDefect() {
            choice = PlayerChoice.DEFECT;
            assertFalse(choice.isCooperate());
        }

        @Test
        @DisplayName("should return true when choice is COOPERATE")
        void shouldReturnTrueWhenChoiceIsCooperate() {
            choice = PlayerChoice.COOPERATE;
            assertTrue(choice.isCooperate());
        }

        @Test
        @DisplayName("should return false when choice is NONE")
        void shouldReturnFalseWhenChoiceIsNone() {
            choice = PlayerChoice.NONE;
            assertFalse(choice.isCooperate());
        }
    }

    @Nested
    @DisplayName("'isNone' method")
    class IsNoneMethod {
        @Test
        @DisplayName("should return false when choice is DEFECT")
        void shouldReturnFalseWhenChoiceIsDefect() {
            choice = PlayerChoice.DEFECT;
            assertFalse(choice.isNone());
        }

        @Test
        @DisplayName("should return false when choice is COOPERATE")
        void shouldReturnFalseWhenChoiceIsCooperate() {
            choice = PlayerChoice.COOPERATE;
            assertFalse(choice.isNone());
        }

        @Test
        @DisplayName("should return true when choice is NONE")
        void shouldReturnTrueWhenChoiceIsNone() {
            choice = PlayerChoice.NONE;
            assertTrue(choice.isNone());
        }
    }

    @Nested
    @DisplayName("'is' method")
    class IsMethod {
        PlayerChoice choiceParameter;

        @Nested
        @DisplayName("with COOPERATE as parameter")
        class WithCooperateAsParameter {
            WithCooperateAsParameter() {
                choiceParameter = PlayerChoice.COOPERATE;
            }

            @Test
            @DisplayName("should return false when choice is DEFECT")
            void shouldReturnFalseWhenChoiceIsDefect() {
                choice = PlayerChoice.DEFECT;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return true when choice is COOPERATE")
            void shouldReturnTrueWhenChoiceIsCooperate() {
                choice = PlayerChoice.COOPERATE;
                assertTrue(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is NONE")
            void shouldReturnFalseWhenChoiceIsNone() {
                choice = PlayerChoice.NONE;
                assertFalse(choice.is(choiceParameter));
            }
        }

        @Nested
        @DisplayName("with DEFECT as parameter")
        class WithDefectAsParameter {
            WithDefectAsParameter() {
                choiceParameter = PlayerChoice.DEFECT;
            }

            @Test
            @DisplayName("should return true when choice is DEFECT")
            void shouldReturnTrueWhenChoiceIsDefect() {
                choice = PlayerChoice.DEFECT;
                assertTrue(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is COOPERATE")
            void shouldReturnFalseWhenChoiceIsCooperate() {
                choice = PlayerChoice.COOPERATE;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is NONE")
            void shouldReturnFalseWhenChoiceIsNone() {
                choice = PlayerChoice.NONE;
                assertFalse(choice.is(choiceParameter));
            }
        }

        @Nested
        @DisplayName("with NONE as parameter")
        class WithNoneAsParameter {
            WithNoneAsParameter() {
                choiceParameter = PlayerChoice.NONE;
            }

            @Test
            @DisplayName("should return false when choice is DEFECT")
            void shouldReturnFalseWhenChoiceIsDefect() {
                choice = PlayerChoice.DEFECT;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is COOPERATE")
            void shouldReturnFalseWhenChoiceIsCooperate() {
                choice = PlayerChoice.COOPERATE;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return true when choice is NONE")
            void shouldReturnTrueWhenChoiceIsNone() {
                choice = PlayerChoice.NONE;
                assertTrue(choice.is(choiceParameter));
            }
        }
    }
}