package org.example.vote.strategy;

import org.example.vote.model.VoteRanked;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RankedChoiceCountingStrategyEdgeTest {

    @Test
    void testCountWithSingleCandidateRepeated() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = List.of(
                new VoteRanked("v1", List.of("A", "A", "A"), 0),
                new VoteRanked("v2", List.of("A"), 0),
                new VoteRanked("v3", List.of("A", "B"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);
        assertEquals(3, result.get("A"));
        assertFalse(result.containsKey("B"));
    }

    @Test
    void testCountWithDuplicateVoterIds() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = List.of(
                new VoteRanked("v1", List.of("A", "B"), 0),
                new VoteRanked("v1", List.of("B", "A"), 0), // Même votant, préférences différentes
                new VoteRanked("v2", List.of("C"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);
        // Le système devrait accepter les votes dupliqués (pas de vérification d'unicité)
        assertTrue(result.containsKey("A") || result.containsKey("B") || result.containsKey("C"));
    }

    @Test
    void testCountWithUnmodifiableList() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = Collections.unmodifiableList(List.of(
                new VoteRanked("v1", List.of("A", "B"), 0),
                new VoteRanked("v2", List.of("B", "A"), 0)
        ));

        // Ne devrait pas lever d'exception
        assertDoesNotThrow(() -> {
            Map<String, Integer> result = strategy.count(votes);
            assertNotNull(result);
        });
    }

    @Test
    void testPrivateMethodsIndirectly() {
        var strategy = new RankedChoiceCountingStrategy();

        // Testez le comportement qui utilise les méthodes privées
        var votes = List.of(
                new VoteRanked("v1", List.of("A", "B", "C", "D", "E"), 0),
                new VoteRanked("v2", List.of("B", "A", "D", "C", "E"), 0),
                new VoteRanked("v3", List.of("C", "D", "E", "A", "B"), 0),
                new VoteRanked("v4", List.of("D", "E", "A", "B", "C"), 0),
                new VoteRanked("v5", List.of("E", "A", "B", "C", "D"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);
        assertFalse(result.isEmpty());
        // Au moins un candidat devrait avoir des votes
        assertTrue(result.values().stream().anyMatch(count -> count > 0));
    }
}