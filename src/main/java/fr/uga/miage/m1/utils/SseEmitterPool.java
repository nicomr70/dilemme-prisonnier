package fr.uga.miage.m1.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class SseEmitterPool{
    @Getter
    public int id = 0;
    @Getter(AccessLevel.NONE)
    private static final Logger LOGGER = Logger.getLogger(SseEmitterPool.class.getPackageName());

    @Getter
    private final List<SseEmitter> allGames = Collections.synchronizedList(new ArrayList<>());



    public void sendAll(Object object){
        List<SseEmitter> deadSse = new ArrayList<>();
        allGames.forEach(value ->{
            try {
                System.out.print(""+value+" "+object);
                value.send(object);
            } catch (IOException e) {
                deadSse.add(value);
                e.printStackTrace();
            }
        });
        allGames.removeAll(deadSse);
    }

    public SseEmitter sseEmitterFactory(String role){
        SseEmitter sse = new SseEmitter(Long.MAX_VALUE);
        sse.onCompletion(() -> LOGGER.info(() -> String.format("SSE emitter of %s is completed. %n", role)));
        sse.onTimeout(() -> {
            LOGGER.warning(() -> String.format("SSE emitter of %s timed out%n", role));
            allGames.remove(sse);
            sse.complete();
        });
        sse.onError(ex ->{
            LOGGER.severe(() -> String.format("SSE emitter of %s got an error : %s%n", role, ex));
            allGames.remove(sse);
            sse.complete();
        });
        allGames.add(sse);
        return sse;
    }

}
