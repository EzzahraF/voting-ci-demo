package org.example.vote.observer;

import org.example.vote.model.Vote;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoggingVoteListenerTest {

    @Test
    void testListenerDoesNotThrow() {
        var listener = new LoggingVoteListener();
        var v = new Vote("v1", "A", 123L);

        assertDoesNotThrow(() -> listener.onVote(v));
    }

}