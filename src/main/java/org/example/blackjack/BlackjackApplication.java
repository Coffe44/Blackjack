package org.example.blackjack;

import java.util.Scanner;

public class BlackjackApplication {

    public static void main(String[] args) {
        // Scanner reads what the player types in the console.
        try (Scanner scanner = new Scanner(System.in)) {
            new BlackjackGame(scanner).play();
        }
    }
}
