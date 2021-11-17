package com.example;

import com.example.models.Game;
import com.example.models.player.Player;
import com.example.models.strategy.StrategyFactory;
import com.example.models.strategy.StrategyType;

public class TestGame {
    public static void main(String[] args) {
        Player playerAlice = new Player("Alice", StrategyFactory.getStrategyFromType(StrategyType.RANDOM));
        Player playerBob = new Player("Bob", StrategyFactory.getStrategyFromType(StrategyType.PAVLOV));
        Game game = new Game(playerAlice, playerBob, 10);
        game.launch();
    }
}
