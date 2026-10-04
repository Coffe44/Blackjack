package org.example.blackjack;

public enum Rank {
    // Each rank has a display label and its starting blackjack value.
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 10),
    JACK("J", 10),
    QUEEN("Q", 10),
    KING("K", 10),
    // Aces start as 11; Hand.value() can lower them to 1 if needed.
    ACE("A", 11);

    private final String label;
    private final int value;

    Rank(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String label() {
        return label;
    }

    public int value() {
        return value;
    }
}
