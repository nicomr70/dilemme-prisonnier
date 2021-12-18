package fr.uga.miage.m1.models.game;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerScore;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import fr.uga.miage.m1.sharedstrategy.StrategyChoice;
import fr.uga.miage.m1.sharedstrategy.StrategyExecutionData;
import fr.uga.miage.m1.utils.SseEmitterPool;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("A game")
class GameTest {
    Game game;
    Player player1, player2;

    private void setHumanPlayers() {
        game.setPlayer1(player1 = new Player("Alice"));
        game.setPlayer2(player2 = new Player("Bob"));
    }

    private void setMockPlayers() {
        game.setPlayer1(player1 = mock(Player.class));
        game.setPlayer2(player2 = mock(Player.class));
    }

    private void simulateTurn(StrategyChoice player1Choice, StrategyChoice player2Choice) {
        game.humanTakeTurn(player1, player1Choice);
        game.humanTakeTurn(player2, player2Choice);
        game.endTurn();
    }

    @BeforeEach
    void setup() {
        game = new Game(42);
    }

    @Nested
    @DisplayName("'calculateTurnScore' method")
    @SuppressWarnings("java:S2699")
    class CalculateTurnScoreMethod {
        private int[] expectedScores;

        private int[] calculateExpectedScores(int player1Points, int player2Points) {
            return new int[] {
                    player1.getPreviousScore() + player1Points,
                    player2.getPreviousScore() + player2Points
            };
        }

        @BeforeEach
        void initGame() {
            setHumanPlayers();
            expectedScores = null;
        }

        @Test
        @DisplayName("should grant 1 point to each player if they both defected")
        void shouldGrant1PointToEachPlayerIfTheyBothDefected() {
            simulateTurn(StrategyChoice.DEFECT, StrategyChoice.DEFECT);
            expectedScores = calculateExpectedScores(
                    PlayerScore.SCORE_DEFECT_BOTH,
                    PlayerScore.SCORE_DEFECT_BOTH
            );
        }

        @Test
        @DisplayName("should grant 3 points to each player if they both cooperated")
        void shouldGrant3PointsToEachPlayerIfTheyBothCooperated() {
            simulateTurn(StrategyChoice.COOPERATE, StrategyChoice.COOPERATE);
            expectedScores = calculateExpectedScores(
                    PlayerScore.SCORE_COOPERATE,
                    PlayerScore.SCORE_COOPERATE
            );
        }

        @Test
        @DisplayName("should grant 5 resp. 0 point(s) to the player who defected resp. cooperated")
        void shouldGrant5Resp0PointsToThePlayerWhoDefectedRespCooperated() {
            simulateTurn(StrategyChoice.DEFECT, StrategyChoice.COOPERATE);
            expectedScores = calculateExpectedScores(
                    PlayerScore.SCORE_DEFECT_ONE,
                    PlayerScore.SCORE_DEFECTED
            );
        }

        @Test
        @DisplayName("should grant 0 resp. 5 point(s) to the player who defected resp. cooperated")
        void shouldGrant0Resp5PointsToThePlayerWhoCooperatedRespDefected() {
            simulateTurn(StrategyChoice.COOPERATE, StrategyChoice.DEFECT);
            expectedScores = calculateExpectedScores(
                    PlayerScore.SCORE_DEFECTED,
                    PlayerScore.SCORE_DEFECT_ONE
            );
        }

        @Test
        @DisplayName("should not update scores when first player choice is NONE")
        void shouldNotUpdateScoreWhenFirstStrategyChoiceIsNone() {
            simulateTurn(StrategyChoice.NONE, StrategyChoice.COOPERATE);
            expectedScores = calculateExpectedScores(0, 0);
        }

        @Test
        @DisplayName("should not update scores when second player choice is NONE")
        void shouldNotUpdateScoreWhenSecondStrategyChoiceIsNone() {
            simulateTurn(StrategyChoice.DEFECT, StrategyChoice.NONE);
            expectedScores = calculateExpectedScores(0, 0);
        }

        @Test
        @DisplayName("should not update scores when both players choice is NONE")
        void shouldNotUpdateScoreWhenBothPlayersChoiceIsNone() {
            simulateTurn(StrategyChoice.NONE, StrategyChoice.NONE);
            expectedScores = calculateExpectedScores(0, 0);
        }

        @AfterEach
        void checkScores() {
            assertEquals(expectedScores[0], player1.getScore());
            assertEquals(expectedScores[1], player2.getScore());
        }
    }

    @Nested
    @DisplayName("'canEndTurn' method")
    class CanEndTurnMethod {
        @BeforeEach
        void initGame() {
            setHumanPlayers();
        }

        @Test
        @DisplayName("should return false if first player can play")
        void shouldReturnFalseIfFirstPlayerCanPlay() {
            player2.disallowToPlay();
            assertFalse(game.canEndTurn());
        }

        @Test
        @DisplayName("should return false if second player can play")
        void shouldReturnFalseIfSecondPlayerCanPlay() {
            player1.disallowToPlay();
            assertFalse(game.canEndTurn());
        }

        @Test
        @DisplayName("should return true if both players can not play anymore")
        void shouldReturnTrueIfBothPlayersCanNotPlayAnymore() {
            player1.disallowToPlay();
            player2.disallowToPlay();
            assertTrue(game.canEndTurn());
        }
    }

    @Nested
    @DisplayName("'getPlayerById' method")
    class GetPlayerByIdMethod {
        @BeforeEach
        void initPlayers() {
            setHumanPlayers();
        }

        @Test
        @DisplayName("should return null if player id is unknown")
        void shouldReturnNullIfPlayerIdIsUnknown() {
            assertNull(game.getPlayerById(-1));
        }

        @Test
        @DisplayName("should return first player when its id is inputted")
        void shouldReturnFirstPlayerWhenItsIdIsInputted() {
            assertEquals(player1, game.getPlayerById(player1.getId()));
        }

        @Test
        @DisplayName("should return second player when its id is inputted")
        void shouldReturnSecondPlayerWhenItsIdIsInputted() {
            assertEquals(player2, game.getPlayerById(player2.getId()));
        }
    }

    @Nested
    @DisplayName("'areAllPlayersHere' method")
    class AreAllPlayersHereMethod {
        @Test
        @DisplayName("should return false if first player has not joined yet")
        void shouldReturnFalseIfFirstPlayerHasNotJoinedYet() {
            game.setPlayer2(mock(Player.class));
            assertFalse(game.areAllPlayersHere());
        }

        @Test
        @DisplayName("should return false if second player has not joined yet")
        void shouldReturnFalseIfSecondPlayerHasNotJoinedYet() {
            game.setPlayer1(mock(Player.class));
            assertFalse(game.areAllPlayersHere());
        }

        @Test
        @DisplayName("should return true if both players have joined")
        void shouldReturnTrueIfBothPlayersHaveJoined() {
            game.setPlayer(mock(Player.class));
            game.setPlayer(mock(Player.class));
            assertTrue(game.areAllPlayersHere());
        }
    }

    @Nested
    @DisplayName("'getOpposingPlayer' method")
    class GetOpposingPlayerMethod {
        @BeforeEach
        void initPlayers() {
            game.setPlayer1(player1 = mock(Player.class));
            game.setPlayer2(player2 = mock(Player.class));
        }

        @Test
        @DisplayName("should return second player with first player as input")
        void shouldReturnSecondPlayerWithFirstPlayerAsInput() {
            assertEquals(player2, game.getOpposingPlayer(player1));
        }

        @Test
        @DisplayName("should return first player with second player as input")
        void shouldReturnFirstPlayerWithSecondPlayerAsInput() {
            assertEquals(player1, game.getOpposingPlayer(player2));
        }

        @Test
        @DisplayName("should return null if player does not exist")
        void shouldReturnNullIfPlayerDoesNotExit() {
            assertNull(game.getOpposingPlayer(mock(Player.class)));
        }
    }

    @Nested
    @DisplayName("'addPlayer' method")
    class AddPlayerMethod {
        @Test
        @DisplayName("should make the first player join the game")
        void shouldMakeTheFirstPlayerJoinTheGame() {
            player1 = mock(Player.class);
            game.addPlayer(player1);
            assertEquals(player1, game.getPlayer1());
        }

        @Test
        @DisplayName("should make the second player join when the first player has already joined")
        void shouldMakeTheSecondPlayerJoinWhenTheFirstPlayerHasAlreadyJoined() {
            game.setPlayer1(mock(Player.class));
            player2 = mock(Player.class);
            game.addPlayer(player2);
            assertEquals(player2, game.getPlayer2());
        }

        @Test
        @DisplayName("should return the joining player to be sent")
        void shouldReturnTheJoiningPlayerToBeSent() {
            Player joiningPlayer = mock(Player.class);
            assertEquals(joiningPlayer, game.addPlayer(joiningPlayer));
        }

        @Test
        @DisplayName("should wake up waiting players when all players are here")
        void shouldWakeUpWaitingPlayersWhenAllPlayersAreHere() {
            setMockPlayers();
            game.poolWaitPlayer = mock(SseEmitterPool.class);
            game.addPlayer(player1 = mock(Player.class));
            verify(game.poolWaitPlayer).sendAll(game);
        }

        @Test
        @DisplayName("should not wake up players when some players are missing")
        void shouldNotWakeUpPlayersWhenSomePlayersAreMissing() {
            game.poolWaitPlayer = mock(SseEmitterPool.class);
            game.addPlayer(mock(Player.class));
            verifyNoInteractions(game.poolWaitPlayer);
        }
    }

    @Nested
    @DisplayName("'takeTurn' method")
    class TakeTurnMethod {
        private void mockPlayersCurrentChoice(StrategyChoice player1Choice, StrategyChoice player2Choice) {
            when(player1.getCurrentChoice()).thenReturn(player1Choice);
            when(player2.getCurrentChoice()).thenReturn(player2Choice);
        }

        @Test
        @DisplayName("should make current player play")
        void shouldMakeCurrentPlayerPlay() throws StrategyException {
            setMockPlayers();
            StrategyChoice player1Choice = StrategyChoice.COOPERATE;
            mockPlayersCurrentChoice(player1Choice, StrategyChoice.DEFECT);
            game.takeTurn(player1.getId(), player1Choice);
            verify(player1).play(player1Choice);
        }

        @Test
        @DisplayName("should make opposing player play if its an AI (i.e. it has a strategy")
        void shouldMakeOpposingPlayerPlayIfItsAnAi() throws Exception {
            game.setTurnCount(4);
            setHumanPlayers();
            StrategyChoice player1Choice = StrategyChoice.COOPERATE;
            IStrategy player2Strategy = mock(IStrategy.class);
            player2.setStrategy(player2Strategy);
            when(player2Strategy.execute(new StrategyExecutionData())).thenReturn(StrategyChoice.DEFECT);
            game.takeTurn(player1.getId(), player1Choice);
            verify(player2Strategy).execute(any(StrategyExecutionData.class));
        }

        @Test
        @DisplayName("should return the game instance to be sent")
        void shouldReturnTheGameInstanceToBeSent() throws StrategyException {
            setMockPlayers();
            StrategyChoice player1Choice = StrategyChoice.COOPERATE;
            mockPlayersCurrentChoice(player1Choice, StrategyChoice.DEFECT);
            assertEquals(game, game.takeTurn(player1.getId(), player1Choice));
        }

        @Test
        @DisplayName("should send game to client when turn ends")
        void shouldSendGameToClientWhenTurnEnds() throws StrategyException {
            setMockPlayers();
            StrategyChoice player1Choice = StrategyChoice.COOPERATE;
            mockPlayersCurrentChoice(player1Choice, StrategyChoice.DEFECT);
            game.poolPlayGame = mock(SseEmitterPool.class);
            game.takeTurn(player1.getId(), player1Choice);
            assertTrue(game.canEndTurn());
            verify(game.poolPlayGame).sendAll(game);
        }
    }
}