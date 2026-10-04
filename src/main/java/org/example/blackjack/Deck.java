package org.example.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> cards = new ArrayList<>();

    public Deck() {
        // Build a standard 52-card deck from every suit and rank combination.
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public void shuffle() {
        // Randomize the card order before dealing.
        Collections.shuffle(cards);
    }

    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The deck is empty.");
        }
        // Remove the dealt card so it cannot be drawn again.
        return cards.removeLast();
    }
}
