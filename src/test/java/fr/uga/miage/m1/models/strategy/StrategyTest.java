package fr.uga.miage.m1.models.strategy;

import fr.uga.miage.m1.exceptions.StrategyException;
import fr.uga.miage.m1.models.game.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.player.PlayerChoice;

abstract class StrategyTest {
    Player player1, player2;
    Game game;

    final static int DEFAULT_MAX_TURN_COUNT = 100;

    void initAiPlayers(StrategyType player1StrategyType, StrategyType player2StrategyType) throws StrategyException {
        player1 = new Player("player1", StrategyFactory.getStrategyFromType(player1StrategyType));
        player2 = new Player("player2", StrategyFactory.getStrategyFromType(player2StrategyType));
    }

    void initMixedPlayers(StrategyType aiPlayerStrategy) throws StrategyException {
        initAiPlayers(aiPlayerStrategy, null);
    }

    void turn() throws StrategyException {
        game.setTurnCount(game.getTurnCount() + 1);
        game.aiTakeTurn(player1);
        game.aiTakeTurn(player2);
        game.endTurn();
    }

    void manualTurn(PlayerChoice humanPlayerChoice) throws StrategyException {
        game.setTurnCount(game.getTurnCount() + 1);
        game.aiTakeTurn(player1);
        game.humanTakeTurn(player2, humanPlayerChoice);
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
}
