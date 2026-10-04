package org.example.blackjack;

public record Card(Rank rank, Suit suit) {

    public int value() {
        // The rank decides the blackjack value; the suit is only for display.
        return rank.value();
    }

    @Override
    public String toString() {
        // Example output: A ♥ for ace of hearts, 10 ♠ for ten of spades.
        return rank.label() + " " + suit.symbol();
    }
}
