package fr.uga.miage.m1.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("The 'random' singleton")
class SingleRandomTest {
    @Test
    @DisplayName("should always return the same instance")
    void shouldAlwaysReturnTheSameInstance() {
        SingleRandom instance1 = SingleRandom.getInstance();
        SingleRandom instance2 = SingleRandom.getInstance();
        assertEquals(instance2, instance1);
    }

    @Test
    @DisplayName("should not return a null instance")
    void shouldNotReturnANullInstance() {
        assertNotNull(SingleRandom.getInstance());
    }
}