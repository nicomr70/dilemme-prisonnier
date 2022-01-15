package fr.uga.miage.m1.utils;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.*;

@Log
public class SseEmitterPool{
    @Getter
    private final List<SseEmitter> pool = Collections.synchronizedList(new ArrayList<>());

    public void sendAll(Object data) {
        List<SseEmitter> deadSseEmitters = new ArrayList<>();
        pool.forEach(sseEmitter -> {
            try {
                log.info(() -> String.format("%s -> %s%n", sseEmitter.toString(), data.toString()));
                sseEmitter.send(data);
            } catch (IOException e) {
                log.warning(() -> String.format("Emitter link was broken...%n%s%n)", e));
                deadSseEmitters.add(sseEmitter);
            }
        });
        pool.removeAll(deadSseEmitters);
    }

    public SseEmitter newEmitter(String role){
        SseEmitter newSseEmitter = new SseEmitter(Long.MAX_VALUE);
        newSseEmitter.onCompletion(() ->
                log.info(() -> String.format("SSE emitter of '%s' is completed. %n", role))
        );
        newSseEmitter.onTimeout(() -> {
            log.warning(() -> String.format("SSE emitter of '%s' timed out%n", role));
            pool.remove(newSseEmitter);
            newSseEmitter.complete();
        });
        newSseEmitter.onError(ex -> {
            log.severe(() -> String.format("SSE emitter of '%s' got an error : %s%n", role, ex));
            pool.remove(newSseEmitter);
            newSseEmitter.complete();
        });
        pool.add(newSseEmitter);
        return newSseEmitter;
    }

}
