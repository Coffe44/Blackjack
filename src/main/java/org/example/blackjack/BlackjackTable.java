package org.example.blackjack;

public class BlackjackTable {
    private static final int DEFAULT_CHIPS = 100;

    private Deck deck;
    private Hand player;
    private Hand dealer;
    private RoundResult result;
    private String message;
    private int wins;
    private int losses;
    private int pushes;
    private int playerChips;
    private int dealerChips;
    private int currentBet;

    public BlackjackTable() {
        this(DEFAULT_CHIPS, DEFAULT_CHIPS);
    }

    public BlackjackTable(int playerChips, int dealerChips) {
        this(playerChips, dealerChips, Math.min(10, Math.min(playerChips, dealerChips)));
    }

    public BlackjackTable(int playerChips, int dealerChips, int startingBet) {
        if (playerChips < 1 || dealerChips < 1) {
            throw new IllegalArgumentException("Both chip amounts must be at least 1.");
        }

        this.playerChips = playerChips;
        this.dealerChips = dealerChips;
        startNewRound(startingBet);
    }

    public void startNewRound() {
        startNewRound(defaultBet());
    }

    public void startNewRound(int bet) {
        validateBet(bet);

        deck = new Deck();
        deck.shuffle();

        player = new Hand();
        dealer = new Hand();

        player.add(deck.draw());
        dealer.add(deck.draw());
        player.add(deck.draw());
        dealer.add(deck.draw());

        currentBet = bet;
        result = RoundResult.IN_PROGRESS;
        message = "Bet " + currentBet + " chips. Choose hit or stand.";

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
            payDealer();
            result = RoundResult.PLAYER_BUST;
            message = "You busted. Dealer wins.";
        } else if (dealer.isBust()) {
            wins++;
            payPlayer();
            result = RoundResult.DEALER_BUST;
            message = "Dealer busted. You win!";
        } else if (player.isBlackjack() && !dealer.isBlackjack()) {
            wins++;
            payPlayer();
            result = RoundResult.PLAYER_BLACKJACK;
            message = "Blackjack! You win!";
        } else if (dealer.isBlackjack() && !player.isBlackjack()) {
            losses++;
            payDealer();
            result = RoundResult.DEALER_BLACKJACK;
            message = "Dealer has blackjack. Dealer wins.";
        } else if (player.value() > dealer.value()) {
            wins++;
            payPlayer();
            result = RoundResult.PLAYER_WIN;
            message = "You win!";
        } else if (player.value() < dealer.value()) {
            losses++;
            payDealer();
            result = RoundResult.DEALER_WIN;
            message = "Dealer wins.";
        } else {
            pushes++;
            result = RoundResult.PUSH;
            message = "Push. Nobody wins.";
        }

        if (playerChips == 0) {
            message += " You are out of chips.";
        } else if (dealerChips == 0) {
            message += " The house is out of chips.";
        } else {
            message += " Place a new bet.";
        }
    }

    private void payPlayer() {
        playerChips += currentBet;
        dealerChips -= currentBet;
    }

    private void payDealer() {
        playerChips -= currentBet;
        dealerChips += currentBet;
    }

    private int defaultBet() {
        return Math.min(10, Math.min(playerChips, dealerChips));
    }

    public void validateBet(int bet) {
        if (bet < 1) {
            throw new IllegalArgumentException("Bet must be at least 1 chip.");
        }
        if (bet > playerChips) {
            throw new IllegalArgumentException("You cannot bet more chips than you have.");
        }
        if (bet > dealerChips) {
            throw new IllegalArgumentException("The house cannot cover that bet.");
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

    public int playerChips() {
        return playerChips;
    }

    public int dealerChips() {
        return dealerChips;
    }

    public int currentBet() {
        return currentBet;
    }

    public boolean hasChipsForAnotherRound() {
        return playerChips > 0 && dealerChips > 0;
    }

    public boolean isRoundOver() {
        return result != RoundResult.IN_PROGRESS;
    }
}
