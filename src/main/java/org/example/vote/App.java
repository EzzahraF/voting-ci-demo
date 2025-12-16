package org.example.vote;

import org.example.vote.factory.RepositoryFactory;
import org.example.vote.observer.LoggingVoteListener;
import org.example.vote.repo.VoteRepository;
import org.example.vote.strategy.PluralityCountingStrategy;

import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String repoType = args.length > 0 ? args[0] : "memory";
        VoteRepository repo = RepositoryFactory.createRepo(repoType);
        VotingCLI cli = new VotingCLI(repo, List.of(new LoggingVoteListener()));

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to VotingApp CLI. Commands: vote, count, reset, exit");
        while (true) {
            System.out.print("> ");
            String cmd = sc.nextLine().trim();
            switch (cmd.toLowerCase()) {
                case "vote" -> {
                    System.out.print("voter id: ");
                    String vid = sc.nextLine().trim();
                    System.out.print("candidate id: ");
                    String cid = sc.nextLine().trim();
                    cli.castVote(vid, cid);
                    System.out.println("Vote recorded.");
                }
                case "count" -> {
                    var res = cli.countVotes(new PluralityCountingStrategy());
                    System.out.println("Results: " + res);
                }
                case "reset" -> {
                    cli.resetVotes();
                    System.out.println("Reset done.");
                }
                case "exit" -> {
                    System.out.println("Bye");
                    return;
                }
                default -> System.out.println("Unknown command");
            }
        }
    }
}