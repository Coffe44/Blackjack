package org.example.blackjack;

public record Card(Rank rank, Suit suit) {

    public int value() {
        // The rank decides the blackjack value; the suit is only for display.
        return rank.value();
    }

    @Override
    public String toString() {
        return rank.label() + " " + suit.symbol();
    }
}
