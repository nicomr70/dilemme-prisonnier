package fr.uga.miage.m1.models.game;

import java.util.*;

public class GamePool {

    private final Map<Integer, Game> games = Collections.synchronizedMap(new HashMap<>());

    public void registerGame(Game game) {
        games.put(game.getId(), game);
    }

    public Game getGame(int gameId) {
        return games.get(gameId);
    }

    public Collection<Game> asCollection() {
        return games.values();
    }
}
