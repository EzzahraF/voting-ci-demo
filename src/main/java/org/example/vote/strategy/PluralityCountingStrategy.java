package org.example.vote.strategy;

import org.example.vote.model.Vote;
import java.util.*;
import java.util.stream.Collectors;

public class PluralityCountingStrategy implements CountingStrategy<Vote> {
    @Override
    public Map<String, Integer> count(List<Vote> votes) {
        Map<String, Integer> result = new HashMap<>();
        for (var v : votes) {
            result.merge(v.candidateId(), 1, Integer::sum);
        }
        return result;
    }
}