package com.example;

import com.example.models.Game;
import com.example.models.Player;
import com.example.models.StrategyFactory;
import com.example.models.StrategyType;

public class TestGame {
    public static void main(String[] args) {
        StrategyFactory strategyFactory = new StrategyFactory();
        Player playerAlice = new Player("Alice", strategyFactory.getRandomStrategy());
        Player playerBob = new Player("Bob", strategyFactory.getStrategyFromType(StrategyType.PAVLOV));
        Game game = new Game(playerAlice, playerBob);
        game.launch();
    }
}
