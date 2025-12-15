package org.example.vote.service;

import org.example.vote.model.Vote;
import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.strategy.CountingStrategy;
import org.example.vote.observer.VoteListener;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VoteServiceComprehensiveTest {

    @Test
    void testAddListenerWithNull() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        assertThrows(NullPointerException.class, () -> service.addListener(null));
    }

    @Test
    void testCountWithStrategyThatReturnsNull() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        service.cast(new Vote("v1", "A", 1));

        CountingStrategy<Vote> nullStrategy = votes -> null;

        // Après correction, devrait retourner une map vide
        Map<String, Integer> result = service.count(nullStrategy);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testCastVoteTriggersAllListeners() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        int[] listener1Count = {0};
        int[] listener2Count = {0};
        int[] listener3Count = {0};

        service.addListener(v -> listener1Count[0]++);
        service.addListener(v -> listener2Count[0]++);
        service.addListener(v -> listener3Count[0]++);

        service.cast(new Vote("v1", "A", 1));
        service.cast(new Vote("v2", "B", 2));

        assertEquals(2, listener1Count[0]);
        assertEquals(2, listener2Count[0]);
        assertEquals(2, listener3Count[0]);
    }

    @Test
    void testListenerExceptionHandling() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        boolean[] normalListenerCalled = {false};
        boolean[] exceptionListenerCalled = {false};

        service.addListener(v -> {
            exceptionListenerCalled[0] = true;
            throw new RuntimeException("Simulated listener failure");
        });

        service.addListener(v -> normalListenerCalled[0] = true);

        assertDoesNotThrow(() -> service.cast(new Vote("v1", "A", 1)));

        assertTrue(exceptionListenerCalled[0]);
        assertTrue(normalListenerCalled[0]);
    }

    @Test
    void testEmptyRepositoryAfterReset() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        service.cast(new Vote("v1", "A", 1));
        service.cast(new Vote("v2", "B", 2));
        service.cast(new Vote("v3", "C", 3));

        assertEquals(3, repo.findAll().size());

        service.reset();

        assertEquals(0, repo.findAll().size());
    }

    @Test
    void testMultipleResets() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        service.cast(new Vote("v1", "A", 1));
        service.reset();
        service.cast(new Vote("v2", "B", 2));
        service.reset();
        service.cast(new Vote("v3", "C", 3));
        service.reset();

        assertEquals(0, repo.findAll().size());
    }

    @Test
    void testCountWithEmptyRepository() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);

        CountingStrategy<Vote> strategy = votes -> {
            Map<String, Integer> result = new java.util.HashMap<>();
            for (Vote v : votes) {
                result.merge(v.candidateId(), 1, Integer::sum);
            }
            return result;
        };

        Map<String, Integer> result = service.count(strategy);
        assertTrue(result.isEmpty());
    }
}