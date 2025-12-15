package org.example.vote.strategy;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CountingStrategyInterfaceTest {

    @Test
    void testPluralityStrategyWithConcurrentModification() {
        var strategy = new PluralityCountingStrategy();
        List<Vote> votes = new ArrayList<>();
        votes.add(new Vote("v1", "A", 1));
        votes.add(new Vote("v2", "B", 2));

        // Simuler une modification pendant l'itération
        Map<String, Integer> result = strategy.count(votes);

        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    @Test
    void testPluralityStrategyWithNullVotesInList() {
        var strategy = new PluralityCountingStrategy();
        List<Vote> votes = new ArrayList<>();
        votes.add(new Vote("v1", "A", 1));
        votes.add(null); // Élément null dans la liste

        assertThrows(NullPointerException.class, () -> strategy.count(votes));
    }
}