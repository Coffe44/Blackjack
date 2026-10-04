package org.example.blackjack;

import javax.swing.SwingUtilities;

public class BlackjackApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BlackjackFrame().setVisible(true));
    }
}
