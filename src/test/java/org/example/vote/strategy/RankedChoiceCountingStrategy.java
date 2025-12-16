package org.example.vote.strategy;

import org.example.vote.model.VoteRanked;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RankedChoiceCountingStrategyTest {

    @Test
    void testSingleWinnerFirstRound() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = List.of(
                new VoteRanked("v1", List.of("A", "B"), 0),
                new VoteRanked("v2", List.of("A", "C"), 0),
                new VoteRanked("v3", List.of("B", "A"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);

        // A devrait avoir 2 votes, B devrait avoir 1 vote
        assertEquals(2, result.get("A"));
        assertEquals(1, result.get("B"));
        assertFalse(result.containsKey("C")); // C n'a pas de votes
    }

    @Test
    void testEliminationRequired() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = List.of(
                new VoteRanked("v1", List.of("C", "B", "A"), 0),
                new VoteRanked("v2", List.of("B", "C", "A"), 0),
                new VoteRanked("v3", List.of("A", "B", "C"), 0),
                new VoteRanked("v4", List.of("A", "C", "B"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);

        // Au moins un candidat doit être compté
        assertFalse(result.isEmpty());
        // A devrait avoir au moins 2 votes
        assertTrue(result.get("A") >= 2);
    }

    @Test
    void testAllEqualVotes() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = List.of(
                new VoteRanked("v1", List.of("A", "B"), 0),
                new VoteRanked("v2", List.of("B", "A"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);

        assertEquals(1, result.get("A"));
        assertEquals(1, result.get("B"));
    }

    @Test
    void testEmptyVoteList() {
        var strategy = new RankedChoiceCountingStrategy();
        Map<String, Integer> result = strategy.count(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void testVotesWithEmptyPreferences() {
        var strategy = new RankedChoiceCountingStrategy();

        var votes = List.of(
                new VoteRanked("v1", List.of(), 0),
                new VoteRanked("v2", List.of("A"), 0),
                new VoteRanked("v3", List.of("A", "B"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);

        assertEquals(2, result.get("A"));
        assertFalse(result.containsKey("B")); // B n'est pas un premier choix
    }

    @Test
    void testNullVoteList() {
        var strategy = new RankedChoiceCountingStrategy();
        Map<String, Integer> result = strategy.count(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSingleVote() {
        var strategy = new RankedChoiceCountingStrategy();
        var votes = List.of(
                new VoteRanked("v1", List.of("A", "B", "C"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);
        assertEquals(1, result.get("A"));
        assertEquals(1, result.size());
    }

    @Test
    void testClearWinnerInFirstRound() {
        var strategy = new RankedChoiceCountingStrategy();
        var votes = List.of(
                new VoteRanked("v1", List.of("A", "B"), 0),
                new VoteRanked("v2", List.of("A", "C"), 0),
                new VoteRanked("v3", List.of("A", "B"), 0),
                new VoteRanked("v4", List.of("B", "A"), 0)
        );

        Map<String, Integer> result = strategy.count(votes);
        // A a 3 votes sur 4, donc majorité claire
        assertEquals(3, result.get("A"));
        assertEquals(1, result.get("B"));
    }
}