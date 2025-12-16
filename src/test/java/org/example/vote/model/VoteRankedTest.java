package org.example.vote.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VoteRankedTest {

    @Test
    void testVoteRankedRecord() {
        VoteRanked v1 = new VoteRanked("v1", List.of("A", "B", "C"), 123L);

        assertEquals("v1", v1.voterId());
        assertEquals(List.of("A", "B", "C"), v1.rankedCandidateIds());
        assertEquals(123L, v1.timestamp());
    }

    @Test
    void testVoteRankedEqualsAndHashCode() {
        VoteRanked v1 = new VoteRanked("v1", List.of("A", "B"), 123L);
        VoteRanked v2 = new VoteRanked("v1", List.of("A", "B"), 123L);
        VoteRanked v3 = new VoteRanked("v2", List.of("C"), 456L);

        assertEquals(v1, v2);
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1, v3);
    }

    @Test
    void testVoteRankedToString() {
        VoteRanked v = new VoteRanked("v1", List.of("A", "B"), 123L);
        String str = v.toString();

        assertTrue(str.contains("v1"));
        assertTrue(str.contains("A"));
        assertTrue(str.contains("123"));
    }

    @Test
    void testVoteRankedEmptyList() {
        VoteRanked v = new VoteRanked("v1", List.of(), 123L);
        assertTrue(v.rankedCandidateIds().isEmpty());
    }
}