package com.example.tests;

import com.example.models.Game;
import com.example.models.player.Player;
import com.example.models.strategy.StrategyFactory;
import com.example.models.strategy.StrategyType;

public class TestGame {
    public static void main(String[] args) {
        Player playerAlice = new Player("Alice", StrategyFactory.getStrategyFromType(StrategyType.DEFECT));
        Player playerBob = new Player("Bob", StrategyFactory.getStrategyFromType(StrategyType.GRADUAL));
        Game game = new Game(playerAlice, playerBob, 20);
        game.launch();
    }
}
