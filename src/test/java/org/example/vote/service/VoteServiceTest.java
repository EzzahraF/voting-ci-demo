package org.example.vote.service;

import org.example.vote.model.Vote;
import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.strategy.PluralityCountingStrategy;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VoteServiceTest {



    @Test
    void testServiceConstructorWithValidRepository() {
        var repo = new InMemoryVoteRepository();
        var service = new VoteService(repo);
        assertNotNull(service);
    }


}