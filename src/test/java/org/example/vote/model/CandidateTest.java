package org.example.vote.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandidateTest {

    @Test
    void testCandidateRecord() {
        Candidate c1 = new Candidate("c1", "Alice");

        // Accesseurs
        assertEquals("c1", c1.id());
        assertEquals("Alice", c1.name());

        // equals et hashCode
        Candidate c2 = new Candidate("c1", "Alice");
        Candidate c3 = new Candidate("c2", "Bob");

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);

        // toString
        assertTrue(c1.toString().contains("c1"));
        assertTrue(c1.toString().contains("Alice"));
    }
}
