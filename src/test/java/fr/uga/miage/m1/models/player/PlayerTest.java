package fr.uga.miage.m1.models.player;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("A player")
class PlayerTest {
    Player player;
    String playerName = "bob";
    IStrategy strategy = mock(IStrategy.class);

    @BeforeEach
    void setUp() {
        player = new Player(playerName, strategy);
        when(strategy.execute(any(StrategyExecutionData.class))).thenReturn(StrategyChoice.COOPERATE);
    }

    @Nested
    @DisplayName("getter")
    class Getter {
        @Test
        @DisplayName("'getName' should return the player name")
        void getNameShouldReturnThePlayerName() {
            assertEquals(playerName, player.getName());
        }

        @Test
        @DisplayName("'canPlay' should return if the player can play")
        void canPlayShouldReturnIfThePlayerCanPlay() {
            assertTrue(player.canPlay());
        }

        @Test
        @DisplayName("'getId' should return the player unique ID")
        void getIdShouldReturnThePlayerUniqueID() {
            Player otherPlayer = new Player("alice", null);
            assertNotEquals(otherPlayer.getId(), player.getId());
        }

        @Test
        @DisplayName("'getPreviousScore' should return previous score")
        void getPreviousScoreShouldReturnPreviousScore() {
            player.increaseScore(17);
            int previousScore = player.getScore();
            player.increaseScore(5);
            assertEquals(previousScore, player.getPreviousScore());
        }

        @Test
        @DisplayName("'hasStrategy' should return whether the player's strategy is set or not")
        void hasStrategyShouldReturnWhetherThePlayersStrategyIsSetOrNot() {
            assertTrue(player.hasStrategy());
            player.setStrategy(null);
            assertFalse(player.hasStrategy());
        }
    }

    @Nested
    @DisplayName("setter")
    class Setter {
        @Test
        @DisplayName("'allowToPlay' should set that player can play")
        void allowToPlayShouldSetThatPlayerCanPlay() {
            player.allowToPlay();
            assertTrue(player.canPlay());
        }

        @Test
        @DisplayName("'disallowToPlay' should set that player can no longer play")
        void disallowToPlayShouldSetThatPlayerCanNoLongerPlay() {
            player.allowToPlay();
            player.disallowToPlay();
            assertFalse(player.canPlay());
        }

        @Test
        @DisplayName("'setStrategy' should change player strategy")
        void setStrategyShouldChangePlayerStrategy() {
            IStrategy newStrategy = mock(IStrategy.class);
            player.setStrategy(newStrategy);
            assertEquals(newStrategy, player.getStrategy());
        }
    }

    @Nested
    @DisplayName("'manualPlay' method")
    class ManualPlayMethod {
        @BeforeEach
        void manualPlay() {
            player.play(StrategyChoice.DEFECT);
        }

        @Test
        @DisplayName("should set that player can no longer play")
        void shouldSetThatPlayerCanNoLongerPlay() {
            assertFalse(player.canPlay());
        }

        @Test
        @DisplayName("should update player current choice")
        void shouldUpdatePlayerCurrentChoice() {
            assertEquals(StrategyChoice.DEFECT, player.getCurrentChoice());
        }
    }

    @Nested
    @DisplayName("'strategyPlay' method")
    class StrategyPlayMethod {
        @Test
        @DisplayName("should set that player can no longer play")
        void shouldSetThatPlayerCanNoLongerPlay() throws StrategyException {
            player.strategyPlay(mock(StrategyExecutionData.class));
            assertFalse(player.canPlay());
        }

        @Test
        @DisplayName("should update player current choice")
        void shouldUpdatePlayerCurrentChoice() throws StrategyException {
            player.strategyPlay(mock(StrategyExecutionData.class));
            assertEquals(StrategyChoice.COOPERATE, player.getCurrentChoice());
        }

        @Test
        @DisplayName("should throw an exception when strategy is not set")
        void shouldThrowAnExceptionWhenStrategyIsNotSet() {
            player.setStrategy(null);
            assertThrows(StrategyException.class, () -> player.strategyPlay(mock(StrategyExecutionData.class)));
        }
    }

    @Nested
    @DisplayName("'getLastChoice' method")
    class GetLastChoiceMethod {
        @Test
        @DisplayName("should return NONE when no choice made")
        void shouldReturnNoneWhenNoChoiceHasBeenMade() {
            assertEquals(StrategyChoice.NONE, player.getLastChoice());
        }

        @Test
        @DisplayName("should return player's last choice")
        void shouldReturnPlayersLastChoice() {
            player.play(StrategyChoice.COOPERATE);
            player.updateChoicesHistory();
            assertEquals(StrategyChoice.COOPERATE, player.getLastChoice());
        }
    }

    @Nested
    @DisplayName("'getChoiceCount' method")
    class GetChoiceCountMethod {
        @BeforeEach
        void simulateGame() {
            player.play(StrategyChoice.COOPERATE);
            player.updateChoicesHistory();
            player.play(StrategyChoice.DEFECT);
            player.updateChoicesHistory();
            player.play(StrategyChoice.COOPERATE);
            player.updateChoicesHistory();
        }

        @Test
        @DisplayName("should not found any NONE choice")
        void shouldNotFoundAnyNoneChoice() {
            assertEquals(0, player.getChoiceCount(StrategyChoice.NONE));
        }

        @Test
        @DisplayName("should found that COOPERATE has been chosen two times")
        void shouldFoundThatCooperateHasBeenChosenTwoTimes() {
            assertEquals(2, player.getChoiceCount(StrategyChoice.COOPERATE));
        }

        @Test
        @DisplayName("should found that DEFECT has been chosen one time")
        void shouldFoundThatDefectHasBeenChosenOneTime() {
            assertEquals(1, player.getChoiceCount(StrategyChoice.DEFECT));
        }
    }

    @Nested
    @DisplayName("'hasCooperatedLastTurn' method")
    class HasCooperatedMethod {
        @Test
        @DisplayName("should return true when last choice is COOPERATE")
        void shouldReturnTrueWhenLastChoiceIsCooperate() {
            player.play(StrategyChoice.COOPERATE);
            player.updateChoicesHistory();
            assertTrue(player.hasCooperatedLastTurn());
        }

        @Test
        @DisplayName("should return false when last choice is DEFECT")
        void shouldReturnFalseWhenLastChoiceIsDefect() {
            player.play(StrategyChoice.DEFECT);
            player.updateChoicesHistory();
            assertFalse(player.hasCooperatedLastTurn());
        }
    }

    @Nested
    @DisplayName("'hasDefectedLastTurn' method")
    class HasDefectedMethod {
        @Test
        @DisplayName("should return true when last choice is DEFECT")
        void shouldReturnTrueWhenLastChoiceIsDefect() {
            player.play(StrategyChoice.DEFECT);
            player.updateChoicesHistory();
            assertTrue(player.hasDefectedLastTurn());
        }

        @Test
        @DisplayName("should return false when last choice is COOPERATE")
        void shouldReturnFalseWhenLastChoiceIsCooperate() {
            player.play(StrategyChoice.COOPERATE);
            player.updateChoicesHistory();
            assertFalse(player.hasDefectedLastTurn());
        }
    }
}