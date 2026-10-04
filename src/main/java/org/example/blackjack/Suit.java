package org.example.blackjack;

public enum Suit {
    // Unicode escapes keep suit symbols stable across terminals and editors.
    CLUBS("\u2663"),
    DIAMONDS("\u2666"),
    HEARTS("\u2665"),
    SPADES("\u2660");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }
}
