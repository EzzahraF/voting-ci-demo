package org.example.vote.strategy;

import java.util.List;
import java.util.Map;

public interface CountingStrategy<T> {
    Map<String, Integer> count(List<T> votes);
}