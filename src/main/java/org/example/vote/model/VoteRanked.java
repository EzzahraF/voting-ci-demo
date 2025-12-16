package org.example.vote.model;

import java.util.List;

public record VoteRanked(String voterId, List<String> rankedCandidateIds, long timestamp) {}
