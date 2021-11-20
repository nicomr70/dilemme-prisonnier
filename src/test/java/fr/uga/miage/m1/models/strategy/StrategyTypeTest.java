package fr.uga.miage.m1.models.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("A strategy type")
class StrategyTypeTest {
    @Test
    @DisplayName("name should not be empty")
    void nameShouldNotBeEmpty() {
        for (StrategyType type : StrategyType.values()) {
            assertNotEquals("", type.getName());
        }
    }
}