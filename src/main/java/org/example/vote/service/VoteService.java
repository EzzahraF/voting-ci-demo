package org.example.vote.service;

import org.example.vote.model.Vote;
import org.example.vote.repo.VoteRepository;
import org.example.vote.strategy.CountingStrategy;
import org.example.vote.observer.VoteListener;
import java.util.*;

public class VoteService {
    private final VoteRepository repo;
    private final List<VoteListener> listeners = new ArrayList<>();

    public VoteService(VoteRepository repo){
        this.repo = Objects.requireNonNull(repo, "Repository cannot be null");
    }

    public void addListener(VoteListener listener) {
        listeners.add(Objects.requireNonNull(listener, "Listener cannot be null"));
    }

    public void cast(Vote v){
        if (v == null) throw new IllegalArgumentException("Vote cannot be null");
        repo.save(v);
        for(var l: listeners) {
            try {
                l.onVote(v);
            } catch(Exception e) {
                System.err.println("Listener failed: " + e.getMessage());
            }
        }
    }

    public Map<String,Integer> count(CountingStrategy<Vote> strategy){
        if(strategy == null) throw new NullPointerException("CountingStrategy cannot be null");
        List<Vote> votes = repo.findAll();
        Map<String, Integer> result = strategy.count(votes);
        return result != null ? result : Collections.emptyMap();
    }

    public void reset(){
        repo.clear();
    }
}