package org.example.vote.repo;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryVoteRepositoryTest {

    @Test
    void testSaveAndFindAll() {
        var repo = new InMemoryVoteRepository();
        var v = new Vote("v1", "C1", System.currentTimeMillis());

        repo.save(v);

        var list = repo.findAll();
        assertEquals(1, list.size());
        assertEquals("C1", list.get(0).candidateId());
    }

    @Test
    void testClear() {
        var repo = new InMemoryVoteRepository();
        repo.save(new Vote("v1", "C1", 0));

        repo.clear();

        assertTrue(repo.findAll().isEmpty());
    }
}