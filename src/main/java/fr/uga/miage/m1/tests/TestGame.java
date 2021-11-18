package fr.uga.miage.m1.tests;

import fr.uga.miage.m1.models.Game;
import fr.uga.miage.m1.models.player.Player;
import fr.uga.miage.m1.models.strategy.StrategyFactory;
import fr.uga.miage.m1.models.strategy.StrategyType;

public class TestGame {
    public static void main(String[] args) {
        Player playerAlice = new Player("Alice", StrategyFactory.getStrategyFromType(StrategyType.DEFECT));
        Player playerBob = new Player("Bob", StrategyFactory.getStrategyFromType(StrategyType.GRADUAL));
        Game game = new Game(playerAlice, playerBob, 20);
        game.launch();
    }
}
