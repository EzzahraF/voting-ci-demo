package org.example.vote;

import org.example.vote.factory.RepositoryFactory;
import org.example.vote.model.Vote;
import org.example.vote.repo.VoteRepository;
import org.example.vote.observer.VoteListener;
import org.example.vote.strategy.CountingStrategy;

import java.util.List;
import java.util.Map;

public class VotingCLI {

    private final VoteServiceWrapper service;

    public VotingCLI(VoteRepository repo, List<VoteListener> listeners) {
        this.service = new VoteServiceWrapper(repo, listeners);
    }

    public void castVote(String voterId, String candidateId) {
        service.cast(new Vote(voterId, candidateId, System.currentTimeMillis()));
    }

    public Map<String, Integer> countVotes(CountingStrategy strategy) {
        return service.count(strategy);
    }

    public void resetVotes() {
        service.reset();
    }

    /** Wrapper interne pour encapsuler VoteService et listeners */
    private static class VoteServiceWrapper {
        private final org.example.vote.service.VoteService svc;

        public VoteServiceWrapper(VoteRepository repo, List<VoteListener> listeners) {
            this.svc = new org.example.vote.service.VoteService(repo);
            if (listeners != null) {
                listeners.forEach(svc::addListener);
            }
        }

        public void cast(Vote vote) {
            svc.cast(vote);
        }

        public Map<String, Integer> count(org.example.vote.strategy.CountingStrategy strategy) {
            return svc.count(strategy);
        }

        public void reset() {
            svc.reset();
        }
    }
}