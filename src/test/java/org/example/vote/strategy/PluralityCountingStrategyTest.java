package org.example.vote.strategy;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PluralityCountingStrategyTest {

    @Test
    void testCountingMap() {
        var strat = new PluralityCountingStrategy();

        var votes = List.of(
                new Vote("v1", "A", 1),
                new Vote("v2", "A", 2),
                new Vote("v3", "B", 3)
        );

        Map<String, Integer> result = strat.count(votes);

        assertEquals(2, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    @Test
    void testEmptyList() {
        var strat = new PluralityCountingStrategy();

        Map<String, Integer> result = strat.count(List.of());

        assertTrue(result.isEmpty());
    }
}