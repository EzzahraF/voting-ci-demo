package org.example.vote;

import org.example.vote.model.Vote;
import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.service.VoteService;
import org.example.vote.strategy.PluralityCountingStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTest {

    @Test
    void testFullFlow() {
        var repo = new InMemoryVoteRepository();
        var svc = new VoteService(repo);

        svc.cast(new Vote("v1", "A", 0));
        svc.cast(new Vote("v2", "A", 0));
        svc.cast(new Vote("v3", "B", 0));

        var result = svc.count(new PluralityCountingStrategy());

        assertEquals(2, result.get("A"));
        assertEquals(1, result.get("B"));
    }
}