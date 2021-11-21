package fr.uga.miage.m1.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class SseEmitterPool{
    @Getter(AccessLevel.NONE)
    private static final Logger LOGGER = Logger.getLogger(SseEmitterPool.class.getPackageName());
    @Getter
    private final List<SseEmitter> pool = Collections.synchronizedList(new ArrayList<>());

    public void sendAll(Object data) {
        List<SseEmitter> deadSseEmitters = new ArrayList<>();
        pool.forEach(sseEmitter -> {
            try {
                LOGGER.info(() -> String.format("%s -> %s", sseEmitter.toString(), data.toString()));
                sseEmitter.send(data);
            } catch (IOException e) {
                deadSseEmitters.add(sseEmitter);
                e.printStackTrace();
            }
        });
        pool.removeAll(deadSseEmitters);
    }

    public SseEmitter newEmitter(String role){
        SseEmitter newSseEmitter = new SseEmitter(Long.MAX_VALUE);
        newSseEmitter.onCompletion(() ->
                LOGGER.info(() -> String.format("SSE emitter of '%s' is completed. %n", role))
        );
        newSseEmitter.onTimeout(() -> {
            LOGGER.warning(() -> String.format("SSE emitter of '%s' timed out%n", role));
            pool.remove(newSseEmitter);
            newSseEmitter.complete();
        });
        newSseEmitter.onError(ex -> {
            LOGGER.severe(() -> String.format("SSE emitter of '%s' got an error : %s%n", role, ex));
            pool.remove(newSseEmitter);
            newSseEmitter.complete();
        });
        pool.add(newSseEmitter);
        return newSseEmitter;
    }

}
