package org.example.vote.service;

import org.example.vote.model.Vote;
import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.strategy.CountingStrategy;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VoteServiceEdgeCasesTest {

    @Test
    void testAddMultipleListeners() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        int[] count1 = {0};
        int[] count2 = {0};

        service.addListener(v -> count1[0]++);
        service.addListener(v -> count2[0]++);

        service.cast(new Vote("v1", "A", 0));

        assertEquals(1, count1[0]);
        assertEquals(1, count2[0]);
    }

    @Test
    void testCountWithStrategyReturningEmptyMap() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        CountingStrategy<Vote> emptyStrategy = votes -> Map.of();
        Map<String, Integer> result = service.count(emptyStrategy);

        assertTrue(result.isEmpty());
    }

    @Test
    void testResetWithNoVotes() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        // Should not throw
        assertDoesNotThrow(() -> service.reset());
        assertTrue(repo.findAll().isEmpty());
    }

    @Test
    void testCastWithEmptyVoteRepository() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        service.cast(new Vote("v1", "A", 0));
        assertEquals(1, repo.findAll().size());
    }
}