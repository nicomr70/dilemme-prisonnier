package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A player choice")
class StrategyChoiceTest {
    StrategyChoice choice;

    @Nested
    @DisplayName("'getOpposite' method")
    class GetOppositeMethod {
        @Test
        @DisplayName("should return NONE when choice is NONE")
        void shouldReturnNoneWhenChoiceIsNone() {
            choice = StrategyChoice.NONE;
            assertEquals(StrategyChoice.NONE, choice.getOpposite());
        }

        @Test
        @DisplayName("should return DEFECT when choice is COOPERATE")
        void shouldReturnDefectWhenChoiceIsCooperate() {
            choice = StrategyChoice.COOPERATE;
            assertEquals(StrategyChoice.DEFECT, choice.getOpposite());
        }

        @Test
        @DisplayName("should return COOPERATE when choice is DEFECT")
        void shouldReturnCooperateWhenChoiceIsDefect() {
            choice = StrategyChoice.DEFECT;
            assertEquals(StrategyChoice.COOPERATE, choice.getOpposite());
        }
    }

    @Nested
    @DisplayName("'isDefect' method")
    class IsDefectMethod {
        @Test
        @DisplayName("should return true when choice is DEFECT")
        void shouldReturnTrueWhenChoiceIsDefect() {
            choice = StrategyChoice.DEFECT;
            assertTrue(choice.isDefect());
        }

        @Test
        @DisplayName("should return false when choice is COOPERATE")
        void shouldReturnFalseWhenChoiceIsCooperate() {
            choice = StrategyChoice.COOPERATE;
            assertFalse(choice.isDefect());
        }

        @Test
        @DisplayName("should return false when choice is NONE")
        void shouldReturnFalseWhenChoiceIsNone() {
            choice = StrategyChoice.NONE;
            assertFalse(choice.isDefect());
        }
    }

    @Nested
    @DisplayName("'isCooperate' method")
    class IsCooperateMethod {
        @Test
        @DisplayName("should return false when choice is DEFECT")
        void shouldReturnFalseWhenChoiceIsDefect() {
            choice = StrategyChoice.DEFECT;
            assertFalse(choice.isCooperate());
        }

        @Test
        @DisplayName("should return true when choice is COOPERATE")
        void shouldReturnTrueWhenChoiceIsCooperate() {
            choice = StrategyChoice.COOPERATE;
            assertTrue(choice.isCooperate());
        }

        @Test
        @DisplayName("should return false when choice is NONE")
        void shouldReturnFalseWhenChoiceIsNone() {
            choice = StrategyChoice.NONE;
            assertFalse(choice.isCooperate());
        }
    }

    @Nested
    @DisplayName("'isNone' method")
    class IsNoneMethod {
        @Test
        @DisplayName("should return false when choice is DEFECT")
        void shouldReturnFalseWhenChoiceIsDefect() {
            choice = StrategyChoice.DEFECT;
            assertFalse(choice.isNone());
        }

        @Test
        @DisplayName("should return false when choice is COOPERATE")
        void shouldReturnFalseWhenChoiceIsCooperate() {
            choice = StrategyChoice.COOPERATE;
            assertFalse(choice.isNone());
        }

        @Test
        @DisplayName("should return true when choice is NONE")
        void shouldReturnTrueWhenChoiceIsNone() {
            choice = StrategyChoice.NONE;
            assertTrue(choice.isNone());
        }
    }

    @Nested
    @DisplayName("'is' method")
    class IsMethod {
        StrategyChoice choiceParameter;

        @Nested
        @DisplayName("with COOPERATE as parameter")
        class WithCooperateAsParameter {
            WithCooperateAsParameter() {
                choiceParameter = StrategyChoice.COOPERATE;
            }

            @Test
            @DisplayName("should return false when choice is DEFECT")
            void shouldReturnFalseWhenChoiceIsDefect() {
                choice = StrategyChoice.DEFECT;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return true when choice is COOPERATE")
            void shouldReturnTrueWhenChoiceIsCooperate() {
                choice = StrategyChoice.COOPERATE;
                assertTrue(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is NONE")
            void shouldReturnFalseWhenChoiceIsNone() {
                choice = StrategyChoice.NONE;
                assertFalse(choice.is(choiceParameter));
            }
        }

        @Nested
        @DisplayName("with DEFECT as parameter")
        class WithDefectAsParameter {
            WithDefectAsParameter() {
                choiceParameter = StrategyChoice.DEFECT;
            }

            @Test
            @DisplayName("should return true when choice is DEFECT")
            void shouldReturnTrueWhenChoiceIsDefect() {
                choice = StrategyChoice.DEFECT;
                assertTrue(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is COOPERATE")
            void shouldReturnFalseWhenChoiceIsCooperate() {
                choice = StrategyChoice.COOPERATE;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is NONE")
            void shouldReturnFalseWhenChoiceIsNone() {
                choice = StrategyChoice.NONE;
                assertFalse(choice.is(choiceParameter));
            }
        }

        @Nested
        @DisplayName("with NONE as parameter")
        class WithNoneAsParameter {
            WithNoneAsParameter() {
                choiceParameter = StrategyChoice.NONE;
            }

            @Test
            @DisplayName("should return false when choice is DEFECT")
            void shouldReturnFalseWhenChoiceIsDefect() {
                choice = StrategyChoice.DEFECT;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return false when choice is COOPERATE")
            void shouldReturnFalseWhenChoiceIsCooperate() {
                choice = StrategyChoice.COOPERATE;
                assertFalse(choice.is(choiceParameter));
            }

            @Test
            @DisplayName("should return true when choice is NONE")
            void shouldReturnTrueWhenChoiceIsNone() {
                choice = StrategyChoice.NONE;
                assertTrue(choice.is(choiceParameter));
            }
        }
    }
}