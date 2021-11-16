package com.example;

import com.example.models.player.Player;
import com.example.models.strategy.StrategyFactory;
import com.example.models.strategy.StrategyType;

public class TestGame {
    public static void main(String[] args) {
        Player playerAlice = new Player("Alice", StrategyFactory.getRandomStrategy());
        Player playerBob = new Player("Bob", StrategyFactory.getStrategyFromType(StrategyType.PAVLOV));
        //Game game = new Game(playerAlice, playerBob);
        //game.launch();
    }
}
