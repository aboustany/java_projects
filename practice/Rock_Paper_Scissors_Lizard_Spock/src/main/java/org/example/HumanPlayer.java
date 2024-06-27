package org.example;

import java.util.Scanner;

class HumanPlayer extends Player {
    public Move makeMove(Scanner scanner) {
        int playerChoice;
        do {
            System.out.println("Please select your move (1 - Rock ; 2 - Paper ; 3 - Scissors ; 4 - Lizard ; 5 - Spock): ");
            playerChoice = scanner.nextInt();
        } while (playerChoice > 5 || playerChoice <= 0);

        return Move.fromID(playerChoice);
    }
}
