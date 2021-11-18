package fr.uga.miage.m1;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.uga.miage.m1.models.Game;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
    private static final Map<Integer, Game> games = new HashMap<>();

    public static void registerGame(Game game) {
        games.put(game.getId(), game);
    }

    public static Game getGame(int gameId) {
        return games.get(gameId);
    }

    public static Collection<Game> getGamesCollection() {
        return games.values();
    }

    public static void main(String[] args){
        SpringApplication.run(RestServer.class, args);
    }

}
