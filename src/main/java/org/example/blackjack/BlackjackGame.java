package org.example.blackjack;

import java.util.Scanner;

public class BlackjackGame {
    private final Scanner scanner;
    private Deck deck;
    // Track the player's record for the current program session.
    private int wins;
    private int losses;
    private int pushes;

    public BlackjackGame(Scanner scanner) {
        this.scanner = scanner;
    }

    public void play() {
        System.out.println("Welcome to Console Blackjack!");
        System.out.println("Try to get closer to 21 than the dealer without going over.");

        // Keep starting new rounds until the player chooses to stop.
        boolean keepPlaying = true;
        while (keepPlaying) {
            playRound();
            printRecord();
            keepPlaying = askYesNo("Play another round? (y/n): ");
        }

        System.out.println("Thanks for playing.");
    }

    private void playRound() {
        // Each round starts with a fresh shuffled deck and empty hands.
        deck = new Deck();
        deck.shuffle();

        Hand player = new Hand();
        Hand dealer = new Hand();

        // Deal two cards to each side, alternating like a real table.
        player.add(deck.draw());
        dealer.add(deck.draw());
        player.add(deck.draw());
        dealer.add(deck.draw());

        System.out.println();
        System.out.println("New round");
        printTable(player, dealer, false);

        // A starting blackjack ends the round immediately.
        if (player.isBlackjack() || dealer.isBlackjack()) {
            finishRound(player, dealer);
            return;
        }

        playerTurn(player, dealer);
        if (!player.isBust()) {
            // Dealer only plays if the player has not already busted.
            dealerTurn(dealer);
        }

        finishRound(player, dealer);
    }

    private void playerTurn(Hand player, Hand dealer) {
        // The player may keep hitting until they stand or go over 21.
        while (!player.isBust()) {
            String move = askMove();
            if (move.equals("s")) {
                break;
            }

            Card card = deck.draw();
            player.add(card);
            System.out.println("You drew " + card + ".");
            printTable(player, dealer, false);
        }
    }

    private void dealerTurn(Hand dealer) {
        System.out.println();
        System.out.println("Dealer's turn");
        // Standard house rule: dealer must draw until reaching at least 17.
        while (dealer.value() < 17) {
            Card card = deck.draw();
            dealer.add(card);
            System.out.println("Dealer draws " + card + ".");
        }
    }

    private void finishRound(Hand player, Hand dealer) {
        System.out.println();
        printTable(player, dealer, true);

        // Decide the winner in order from automatic losses/wins to score comparison.
        if (player.isBust()) {
            losses++;
            System.out.println("You busted. Dealer wins.");
        } else if (dealer.isBust()) {
            wins++;
            System.out.println("Dealer busted. You win!");
        } else if (player.isBlackjack() && !dealer.isBlackjack()) {
            wins++;
            System.out.println("Blackjack! You win!");
        } else if (dealer.isBlackjack() && !player.isBlackjack()) {
            losses++;
            System.out.println("Dealer has blackjack. Dealer wins.");
        } else if (player.value() > dealer.value()) {
            wins++;
            System.out.println("You win!");
        } else if (player.value() < dealer.value()) {
            losses++;
            System.out.println("Dealer wins.");
        } else {
            pushes++;
            System.out.println("Push. Nobody wins.");
        }
    }

    private void printTable(Hand player, Hand dealer, boolean revealDealer) {
        // During the round, hide one dealer card. At the end, show everything.
        if (revealDealer) {
            System.out.println("Dealer: " + dealer + " (" + dealer.value() + ")");
        } else {
            System.out.println("Dealer: " + dealer.visibleCards() + " + [hidden]");
        }
        System.out.println("You:    " + player + " (" + player.value() + ")");
    }

    private String askMove() {
        while (true) {
            System.out.print("Hit or stand? (h/s): ");
            // If input ends unexpectedly, stand so the game exits cleanly.
            if (!scanner.hasNextLine()) {
                System.out.println();
                return "s";
            }
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("h") || input.equals("hit")) {
                return "h";
            }
            if (input.equals("s") || input.equals("stand")) {
                return "s";
            }
            System.out.println("Please enter h to hit or s to stand.");
        }
    }

    private boolean askYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            // If input ends unexpectedly, treat it as "no".
            if (!scanner.hasNextLine()) {
                System.out.println();
                return false;
            }
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }

    private void printRecord() {
        System.out.println("Record: " + wins + " wins, " + losses + " losses, " + pushes + " pushes");
        System.out.println();
    }
}
