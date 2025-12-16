package org.example.vote;

import org.example.vote.repo.InMemoryVoteRepository;
import org.example.vote.observer.VoteListener;
import org.example.vote.strategy.CountingStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VotingCLITest {

    @Test
    void testVotingCLIWithNullListeners() {
        var repo = new InMemoryVoteRepository();
        var cli = new VotingCLI(repo, null);

        // Ne devrait pas lever d'exception
        assertNotNull(cli);
    }

    @Test
    void testCastVoteWithNullOrEmptyStrings() {
        var repo = new InMemoryVoteRepository();
        var cli = new VotingCLI(repo, List.of());

        // Ces appels ne devraient pas lever d'exception
        // (la validation serait dans VoteService)
        cli.castVote(null, null);
        cli.castVote("", "");
        cli.castVote("voter1", "");

        assertEquals(3, repo.findAll().size());
    }

    @Test
    void testCountVotesWithNullStrategy() {
        var repo = new InMemoryVoteRepository();
        var cli = new VotingCLI(repo, List.of());

        // Ajouter quelques votes
        cli.castVote("v1", "A");
        cli.castVote("v2", "B");

        // Doit lever une exception car la stratégie ne peut pas être null
        assertThrows(NullPointerException.class, () -> cli.countVotes(null));
    }

    @Test
    void testVotingCLIWithEmptyRepository() {
        var repo = new InMemoryVoteRepository();
        var cli = new VotingCLI(repo, List.of());

        // Compter avec un repository vide
        CountingStrategy strategy = votes -> Map.of();
        Map<String, Integer> result = cli.countVotes(strategy);
        assertTrue(result.isEmpty());
    }

    @Test
    void testListenerNotificationInCLI() {
        var repo = new InMemoryVoteRepository();
        boolean[] listenerCalled = {false};

        VoteListener listener = vote -> listenerCalled[0] = true;
        var cli = new VotingCLI(repo, List.of(listener));

        cli.castVote("v1", "A");
        assertTrue(listenerCalled[0]);
    }
}