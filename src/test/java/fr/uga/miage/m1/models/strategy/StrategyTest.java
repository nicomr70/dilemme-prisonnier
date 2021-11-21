package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class StrategyTest {
    Player player1, player2;
    Game game;

    final static int DEFAULT_MAX_TURN_COUNT = 100;

    Game initGameWithAiPlayers(int maxTurnCount, StrategyType player1StrategyType, StrategyType player2StrategyType) throws StrategyException {
        player1 = new Player("player1", StrategyFactory.getStrategyFromType(player1StrategyType));
        player2 = new Player("player2", StrategyFactory.getStrategyFromType(player2StrategyType));
        return game = new Game(maxTurnCount, player1, player2);
    }

    Game initGameWithAiPlayers(StrategyType player1StrategyType, StrategyType player2StrategyType) throws StrategyException {
        return initGameWithAiPlayers(DEFAULT_MAX_TURN_COUNT, player1StrategyType, player2StrategyType);
    }

    Game initGameWithMixedPlayers(int maxTurnCount, StrategyType aiPlayerStrategy) throws StrategyException {
        return initGameWithAiPlayers(maxTurnCount, aiPlayerStrategy, null);
    }

    Game initGameWithMixedPlayers(StrategyType aiPlayerStrategy) throws StrategyException {
        return initGameWithMixedPlayers(DEFAULT_MAX_TURN_COUNT, aiPlayerStrategy);
    }

    void turn() throws StrategyException {
        game.setTurnCount(game.getTurnCount() + 1);
        game.aiTakeTurn(player1);
        game.aiTakeTurn(player2);
        game.endTurn();
        System.out.println("Tour "+game.getTurnCount()+" | P1 : "+player1.getLastChoice()+" ; P2 : "+player2.getLastChoice());
    }

    void manualTurn(PlayerChoice humanPlayerChoice) throws StrategyException {
        game.setTurnCount(game.getTurnCount() + 1);
        game.aiTakeTurn(player1);
        game.humanTakeTurn(player2, humanPlayerChoice);
        game.endTurn();
        System.out.println("Tour "+game.getTurnCount()+" | P1 : "+player1.getLastChoice()+" ; P2 : "+player2.getLastChoice());
    }

    void iterateTurn(int count) throws StrategyException {
        while (count > 0 && game.getTurnCount() < game.getMaxTurnCount()) {
            turn();
            count--;
        }
    }

    void iterateManualTurn(int count, PlayerChoice humanPlayerChoice) throws StrategyException {
        while (count > 0 && game.getTurnCount() < game.getMaxTurnCount()) {
            manualTurn(humanPlayerChoice);
            count--;
        }
    }

    void assertWasLastPlayed(Player player, PlayerChoice choice) {
        assertEquals(choice, player.getLastChoice());
    }
}
