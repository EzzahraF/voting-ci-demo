package org.example.vote.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VoteTest {

    @Test
    void testVoteConstructorValidation() {
        // Test avec des valeurs nulles - les records Java n'empêchent pas null
        Vote vote = new Vote(null, null, 0);
        assertNull(vote.voterId());
        assertNull(vote.candidateId());
    }

    @Test
    void testVoteRankedWithNullValues() {
        VoteRanked vote = new VoteRanked(null, null, 0);
        assertNull(vote.voterId());
        assertNull(vote.rankedCandidateIds());
    }

    @Test
    void testCandidateWithNullValues() {
        Candidate candidate = new Candidate(null, null);
        assertNull(candidate.id());
        assertNull(candidate.name());
    }

    @Test
    void testVoteImmutability() {
        Vote vote = new Vote("v1", "A", 123L);
        // Vérifier que les getters fonctionnent
        assertEquals("v1", vote.voterId());
        assertEquals("A", vote.candidateId());
        assertEquals(123L, vote.timestamp());
    }
}