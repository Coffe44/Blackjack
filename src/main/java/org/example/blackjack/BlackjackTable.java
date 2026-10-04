package org.example.blackjack;

public class BlackjackTable {
    private Deck deck;
    private Hand player;
    private Hand dealer;
    private RoundResult result;
    private String message;
    private int wins;
    private int losses;
    private int pushes;

    public BlackjackTable() {
        startNewRound();
    }

    public void startNewRound() {
        deck = new Deck();
        deck.shuffle();

        player = new Hand();
        dealer = new Hand();

        player.add(deck.draw());
        dealer.add(deck.draw());
        player.add(deck.draw());
        dealer.add(deck.draw());

        result = RoundResult.IN_PROGRESS;
        message = "Choose hit or stand.";

        if (player.isBlackjack() || dealer.isBlackjack()) {
            finishRound();
        }
    }

    public void hit() {
        if (isRoundOver()) {
            return;
        }

        Card card = deck.draw();
        player.add(card);
        message = "You drew " + card + ".";

        if (player.isBust()) {
            finishRound();
        }
    }

    public void stand() {
        if (isRoundOver()) {
            return;
        }

        while (dealer.value() < 17) {
            dealer.add(deck.draw());
        }

        finishRound();
    }

    private void finishRound() {
        if (player.isBust()) {
            losses++;
            result = RoundResult.PLAYER_BUST;
            message = "You busted. Dealer wins.";
        } else if (dealer.isBust()) {
            wins++;
            result = RoundResult.DEALER_BUST;
            message = "Dealer busted. You win!";
        } else if (player.isBlackjack() && !dealer.isBlackjack()) {
            wins++;
            result = RoundResult.PLAYER_BLACKJACK;
            message = "Blackjack! You win!";
        } else if (dealer.isBlackjack() && !player.isBlackjack()) {
            losses++;
            result = RoundResult.DEALER_BLACKJACK;
            message = "Dealer has blackjack. Dealer wins.";
        } else if (player.value() > dealer.value()) {
            wins++;
            result = RoundResult.PLAYER_WIN;
            message = "You win!";
        } else if (player.value() < dealer.value()) {
            losses++;
            result = RoundResult.DEALER_WIN;
            message = "Dealer wins.";
        } else {
            pushes++;
            result = RoundResult.PUSH;
            message = "Push. Nobody wins.";
        }
    }

    public Hand player() {
        return player;
    }

    public Hand dealer() {
        return dealer;
    }

    public RoundResult result() {
        return result;
    }

    public String message() {
        return message;
    }

    public int wins() {
        return wins;
    }

    public int losses() {
        return losses;
    }

    public int pushes() {
        return pushes;
    }

    public boolean isRoundOver() {
        return result != RoundResult.IN_PROGRESS;
    }
}
