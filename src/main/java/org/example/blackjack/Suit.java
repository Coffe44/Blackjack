package org.example.blackjack;

public enum Suit {
    // Suit symbols keep the console output compact and card-like.
    CLUBS("♣"),
    DIAMONDS("♦"),
    HEARTS("♥"),
    SPADES("♠");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
