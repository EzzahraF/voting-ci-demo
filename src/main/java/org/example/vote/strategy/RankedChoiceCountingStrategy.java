package org.example.vote.strategy;

import org.example.vote.model.VoteRanked;
import java.util.*;
import java.util.stream.Collectors;

public class RankedChoiceCountingStrategy implements CountingStrategy<VoteRanked> {

    @Override
    public Map<String, Integer> count(List<VoteRanked> votes) {
        if (votes == null || votes.isEmpty()) {
            return new HashMap<>();
        }

        // Copie des votes avec leurs préférences originales
        List<VoteRanked> activeVotes = new ArrayList<>(votes);
        Set<String> allCandidates = getAllCandidates(activeVotes);
        Map<String, Integer> result = new HashMap<>();

        // Initialiser tous les candidats avec 0
        for (String candidate : allCandidates) {
            result.put(candidate, 0);
        }

        int round = 1;
        while (true) {
            // Compter les premiers choix
            Map<String, Integer> roundCount = countFirstChoices(activeVotes);

            // Ajouter les résultats au résultat final
            roundCount.forEach((candidate, count) -> {
                result.put(candidate, result.get(candidate) + count);
            });

            // Calculer le total des votes actifs (avec préférences)
            int totalActiveVotes = (int) activeVotes.stream()
                    .filter(v -> !v.rankedCandidateIds().isEmpty())
                    .count();

            if (totalActiveVotes == 0) {
                break;
            }

            // Vérifier si un candidat a la majorité
            Optional<Map.Entry<String, Integer>> winner = roundCount.entrySet().stream()
                    .filter(e -> e.getValue() > totalActiveVotes / 2)
                    .findFirst();

            if (winner.isPresent()) {
                // Le gagnant a été trouvé
                return result.entrySet().stream()
                        .filter(e -> e.getValue() > 0)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            }

            // Trouver le candidat avec le moins de votes
            int minVotes = roundCount.values().stream()
                    .min(Integer::compareTo)
                    .orElse(0);

            List<String> toEliminate = roundCount.entrySet().stream()
                    .filter(e -> e.getValue() == minVotes)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // Éliminer ces candidats des préférences
            activeVotes = activeVotes.stream()
                    .map(v -> {
                        List<String> newPrefs = new ArrayList<>(v.rankedCandidateIds());
                        newPrefs.removeAll(toEliminate);
                        return new VoteRanked(v.voterId(), newPrefs, v.timestamp());
                    })
                    .collect(Collectors.toList());

            round++;

            // Arrêter après un certain nombre de rounds pour éviter une boucle infinie
            if (round > allCandidates.size() * 2) {
                break;
            }
        }

        // Retourner uniquement les candidats avec des votes
        return result.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<String, Integer> countFirstChoices(List<VoteRanked> votes) {
        Map<String, Integer> count = new HashMap<>();
        for (VoteRanked vote : votes) {
            if (!vote.rankedCandidateIds().isEmpty()) {
                String firstChoice = vote.rankedCandidateIds().get(0);
                count.merge(firstChoice, 1, Integer::sum);
            }
        }
        return count;
    }

    private Set<String> getAllCandidates(List<VoteRanked> votes) {
        Set<String> candidates = new HashSet<>();
        for (VoteRanked vote : votes) {
            candidates.addAll(vote.rankedCandidateIds());
        }
        return candidates;
    }
}