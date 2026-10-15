package org.example.blackjack;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

public class BlackjackFrame extends JFrame {
    private static final Color TABLE_GREEN = new Color(22, 94, 65);
    private static final Color PANEL_GREEN = new Color(31, 119, 82);
    private static final Color CARD_RED = new Color(171, 39, 39);
    private static final Color TEXT_LIGHT = new Color(247, 249, 245);
    private static final int DEFAULT_STARTING_CHIPS = 100;

    private final BlackjackTable table;
    private final JPanel dealerCards = new JPanel();
    private final JPanel playerCards = new JPanel();
    private final JLabel dealerValue = new JLabel();
    private final JLabel playerValue = new JLabel();
    private final JLabel message = new JLabel();
    private final JLabel record = new JLabel();
    private final JLabel chips = new JLabel();
    private final JTextField betField = new JTextField(5);
    private final JButton hitButton = new JButton("Hit");
    private final JButton standButton = new JButton("Stand");
    private final JButton newRoundButton = new JButton("New Round");

    public BlackjackFrame() {
        super("Blackjack");
        int[] startingValues = promptForStartingValues();
        table = new BlackjackTable(startingValues[0], startingValues[1], startingValues[2]);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(760, 560));
        setLocationByPlatform(true);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setBorder(BorderFactory.createEmptyBorder(22, 22, 22, 22));
        content.setBackground(TABLE_GREEN);
        setContentPane(content);

        content.add(createHeader(), BorderLayout.NORTH);
        content.add(createTablePanel(), BorderLayout.CENTER);
        content.add(createControls(), BorderLayout.SOUTH);

        hitButton.addActionListener(event -> {
            table.hit();
            updateView();
        });
        standButton.addActionListener(event -> {
            table.stand();
            updateView();
        });
        newRoundButton.addActionListener(event -> {
            startNewRoundFromBetField();
        });

        updateView();
        pack();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("Blackjack");
        title.setForeground(TEXT_LIGHT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 32f));

        record.setForeground(TEXT_LIGHT);
        record.setHorizontalAlignment(SwingConstants.RIGHT);
        record.setFont(record.getFont().deriveFont(Font.BOLD, 16f));

        chips.setForeground(TEXT_LIGHT);
        chips.setHorizontalAlignment(SwingConstants.RIGHT);
        chips.setFont(chips.getFont().deriveFont(Font.BOLD, 16f));

        JPanel stats = new JPanel(new GridLayout(2, 1, 0, 4));
        stats.setOpaque(false);
        stats.add(record);
        stats.add(chips);

        header.add(title, BorderLayout.WEST);
        header.add(stats, BorderLayout.EAST);
        return header;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new GridLayout(2, 1, 0, 18));
        tablePanel.setOpaque(false);
        tablePanel.add(createHandPanel("Dealer", dealerCards, dealerValue));
        tablePanel.add(createHandPanel("You", playerCards, playerValue));
        return tablePanel;
    }

    private JPanel createHandPanel(String title, JPanel cardsPanel, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout(14, 14));
        panel.setBackground(PANEL_GREEN);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(236, 224, 181), 2),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(TEXT_LIGHT);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 22f));

        valueLabel.setForeground(TEXT_LIGHT);
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        valueLabel.setFont(valueLabel.getFont().deriveFont(Font.BOLD, 18f));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(titleLabel, BorderLayout.WEST);
        top.add(valueLabel, BorderLayout.EAST);

        cardsPanel.setOpaque(false);
        cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.X_AXIS));

        panel.add(top, BorderLayout.NORTH);
        panel.add(cardsPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createControls() {
        JPanel controls = new JPanel(new BorderLayout(18, 0));
        controls.setOpaque(false);

        message.setForeground(TEXT_LIGHT);
        message.setFont(message.getFont().deriveFont(Font.BOLD, 18f));

        JPanel buttons = new JPanel(new GridLayout(1, 3, 10, 0));
        buttons.setOpaque(false);
        buttons.add(hitButton);
        buttons.add(standButton);
        buttons.add(newRoundButton);

        JPanel betting = new JPanel();
        betting.setOpaque(false);
        JLabel betLabel = new JLabel("Bet");
        betLabel.setForeground(TEXT_LIGHT);
        betLabel.setFont(betLabel.getFont().deriveFont(Font.BOLD, 16f));
        betField.setHorizontalAlignment(SwingConstants.RIGHT);
        betting.add(betLabel);
        betting.add(betField);

        JPanel actions = new JPanel(new BorderLayout(10, 0));
        actions.setOpaque(false);
        actions.add(betting, BorderLayout.WEST);
        actions.add(buttons, BorderLayout.EAST);

        controls.add(message, BorderLayout.CENTER);
        controls.add(actions, BorderLayout.EAST);
        return controls;
    }

    private void updateView() {
        boolean revealDealer = table.isRoundOver();

        renderCards(dealerCards, table.dealer().cards(), !revealDealer);
        renderCards(playerCards, table.player().cards(), false);

        dealerValue.setText(revealDealer ? "Value: " + table.dealer().value() : "Value: ?");
        playerValue.setText("Value: " + table.player().value());
        message.setText(table.message());
        record.setText("Wins " + table.wins() + "   Losses " + table.losses() + "   Pushes " + table.pushes());
        chips.setText("You " + table.playerChips() + " chips   House " + table.dealerChips() + " chips");
        betField.setText(String.valueOf(table.currentBet()));

        hitButton.setEnabled(!table.isRoundOver());
        standButton.setEnabled(!table.isRoundOver());
        newRoundButton.setEnabled(table.isRoundOver() && table.hasChipsForAnotherRound());
        betField.setEnabled(table.isRoundOver() && table.hasChipsForAnotherRound());

        revalidate();
        repaint();
    }

    private void startNewRoundFromBetField() {
        try {
            int bet = parsePositiveInt(betField.getText(), "Bet");
            table.startNewRound(bet);
            updateView();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Invalid Bet", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int[] promptForStartingValues() {
        JTextField playerChips = new JTextField(String.valueOf(DEFAULT_STARTING_CHIPS), 8);
        JTextField dealerChips = new JTextField(String.valueOf(DEFAULT_STARTING_CHIPS), 8);
        JTextField startingBet = new JTextField("10", 8);

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.add(new JLabel("Player chips"));
        panel.add(playerChips);
        panel.add(new JLabel("House chips"));
        panel.add(dealerChips);
        panel.add(new JLabel("Starting bet"));
        panel.add(startingBet);

        while (true) {
            int option = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Starting Chips",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option != JOptionPane.OK_OPTION) {
                return new int[]{DEFAULT_STARTING_CHIPS, DEFAULT_STARTING_CHIPS, 10};
            }

            try {
                int parsedPlayerChips = parsePositiveInt(playerChips.getText(), "Player chips");
                int parsedDealerChips = parsePositiveInt(dealerChips.getText(), "House chips");
                int parsedStartingBet = parsePositiveInt(startingBet.getText(), "Starting bet");
                validateBet(parsedStartingBet, parsedPlayerChips, parsedDealerChips);
                return new int[]{parsedPlayerChips, parsedDealerChips, parsedStartingBet};
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Invalid Starting Values", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void validateBet(int bet, int playerChips, int dealerChips) {
        if (bet > playerChips) {
            throw new IllegalArgumentException("You cannot bet more chips than you have.");
        }
        if (bet > dealerChips) {
            throw new IllegalArgumentException("The house cannot cover that bet.");
        }
    }

    private int parsePositiveInt(String text, String label) {
        try {
            int value = Integer.parseInt(text.trim());
            if (value < 1) {
                throw new NumberFormatException();
            }
            return value;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(label + " must be a whole number greater than 0.");
        }
    }

    private void renderCards(JPanel target, List<Card> cards, boolean hideSecondCard) {
        target.removeAll();

        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) {
                target.add(Box.createHorizontalStrut(12));
            }

            if (hideSecondCard && i == 1) {
                target.add(createHiddenCard());
            } else {
                target.add(createCardLabel(cards.get(i)));
            }
        }
    }

    private JLabel createCardLabel(Card card) {
        JLabel label = new JLabel("<html><center>" + card.rank().label() + "<br>" + card.suit().symbol() + "</center></html>");
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setForeground(isRed(card.suit()) ? CARD_RED : Color.BLACK);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 28f));
        label.setBorder(BorderFactory.createLineBorder(new Color(32, 32, 32), 2));
        label.setPreferredSize(new Dimension(82, 116));
        label.setMaximumSize(new Dimension(82, 116));
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JLabel createHiddenCard() {
        JLabel label = new JLabel("<html><center>&#9670;<br>&#10022;<br>&#9670;</center></html>");
        label.setOpaque(true);
        label.setBackground(new Color(34, 55, 114));
        label.setForeground(new Color(236, 224, 181));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 24f));
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        label.setPreferredSize(new Dimension(82, 116));
        label.setMaximumSize(new Dimension(82, 116));
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        return label;
    }

    private boolean isRed(Suit suit) {
        return suit == Suit.HEARTS || suit == Suit.DIAMONDS;
    }
}
