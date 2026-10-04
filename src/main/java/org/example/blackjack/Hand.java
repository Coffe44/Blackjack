package org.example.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void add(Card card) {
        // Add a newly dealt card to this hand.
        cards.add(card);
    }

    public int value() {
        // Start by counting aces as 11.
        int total = cards.stream()
                .mapToInt(Card::value)
                .sum();

        long aces = cards.stream()
                .filter(card -> card.rank() == Rank.ACE)
                .count();

        // If the hand is over 21, turn aces from 11 into 1 until it is safe.
        while (total > 21 && aces > 0) {
            total -= 10;
            aces--;
        }

        return total;
    }

    public boolean isBust() {
        // A hand busts when its best value is above 21.
        return value() > 21;
    }

    public boolean isBlackjack() {
        // Blackjack means exactly two cards adding up to 21.
        return cards.size() == 2 && value() == 21;
    }

    public String visibleCards() {
        // Used while the dealer's second card is hidden.
        if (cards.isEmpty()) {
            return "";
        }
        return cards.getFirst().toString();
    }

    @Override
    public String toString() {
        // Join the cards into a readable list for console output.
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }
}
