package fr.uga.miage.m1.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.*;

@DisplayName("A SSE emitter pool")
class SseEmitterPoolTest {
    SseEmitterPool sseEmitterPool;
    List<SseEmitter> emittersList;

    @BeforeEach
    void setup() {
        sseEmitterPool = new SseEmitterPool();
        emittersList = sseEmitterPool.getPool();
    }

    @Nested
    @DisplayName("'newEmitter' method")
    class newEmitterMethod {
        @Test
        @DisplayName("should add a new SSE emitter to the pool")
        void shouldAddANewSseEmitterToThePool() {
            int originalSize = sseEmitterPool.getPool().size();
            sseEmitterPool.newEmitter("a random role");
            assertEquals(originalSize + 1, sseEmitterPool.getPool().size());
        }

        @Test
        @DisplayName("should return the newly created SSE emitter")
        void shouldReturnTheNewlyCreatedSseEmitter() {
            SseEmitter returnedSseEmitter = sseEmitterPool.newEmitter("another role bites the dust");
            assertTrue(sseEmitterPool.getPool().contains(returnedSseEmitter));
        }
    }

    @Nested
    @DisplayName("'sendAll' method")
    class SendAllMethod {
        @Test
        @DisplayName("should make all emitters in pool send data")
        void shouldMakeAllEmittersInPoolSendDate() throws IOException {
            String data = "some data";
            SseEmitter mockedEmitter1 = mock(SseEmitter.class);
            SseEmitter mockedEmitter2 = mock(SseEmitter.class);
            doNothing().when(mockedEmitter1).send(any());
            doNothing().when(mockedEmitter2).send(any());
            emittersList.add(mockedEmitter1);
            emittersList.add(mockedEmitter2);
            sseEmitterPool.sendAll(data);
            verify(mockedEmitter1).send(data);
            verify(mockedEmitter2).send(data);
        }

        @Test
        @DisplayName("should remove an emitter when its link is broken")
        void shouldRemoveAnEmitterWhenItsLinkIsBroken() throws IOException {
            String data = "stuff";
            SseEmitter mockedEmitter = mock(SseEmitter.class);
            doAnswer(invocationOnMock -> {
                throw new IOException();
            }).when(mockedEmitter).send(data);
            emittersList.add(mockedEmitter);
            sseEmitterPool.sendAll(data);
            verify(mockedEmitter).send(data);
            assertFalse(emittersList.contains(mockedEmitter));
        }
    }
}