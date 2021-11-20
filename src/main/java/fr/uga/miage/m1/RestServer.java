package fr.uga.miage.m1;
import fr.uga.miage.m1.models.game.GamePool;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServer {
    @Getter
    private static final GamePool gamePool = new GamePool();


    public static void main(String[] args){
        SpringApplication.run(RestServer.class, args);
    }

}
