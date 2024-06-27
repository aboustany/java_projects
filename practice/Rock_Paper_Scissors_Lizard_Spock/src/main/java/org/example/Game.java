package org.example;

import java.sql.SQLOutput;
import java.util.Scanner;

class Game {
    private final Player human;
    private final Player computer;
    private int draws;


    public Game(Player human, Player computer) {
        this.human = human;
        this.computer = computer;
    }

    public void startGame() {
        Scanner sc = new Scanner(System.in);
        do {
            playRounds(sc);
            printSummary();
        } while (askToPlayAgain(sc));
    }

    private void playRounds(Scanner sc) {

        System.out.println("How many rounds would you like to play? (Min: 1, Max: 10) : ");
        int numOfRounds = sc.nextInt();

        if(numOfRounds <=0 || numOfRounds>10) {
            System.out.println("Invalid number of rounds");
            return;
        }

        human.resetScore();
        computer.resetScore();
        draws = 0;

        for (int i = 1; i <= numOfRounds; i++) {
            System.out.println("ROUND " + i + " STARTED!");

            Move playerChoice = ((HumanPlayer)human).makeMove(sc);
            Move npcChoice = ((ComputerPlayer)computer).makeMove();

            System.out.println("PLAYER has selected " + playerChoice.getName());
            System.out.println("COMPUTER has selected " + npcChoice.getName());

            String winner = RuleSet.determineWinner(playerChoice.getMoveID(), npcChoice.getMoveID());
            String combo = Move.moveCombinations(playerChoice.getMoveID(), npcChoice.getMoveID());

            System.out.println(combo);

            switch (winner) {
                case "PLAYER":
                    System.out.println("PLAYER HAS WON THIS ROUND.");
                    human.incrementScore();
                    break;
                case "COMPUTER":
                    System.out.println("COMPUTER HAS WON THIS ROUND.");
                    computer.incrementScore();
                    break;
                case "DRAW":
                    System.out.println("THIS ROUND ENDED IN A DRAW.");
                    draws++;
                    break;
            }
        }
    }

    private void printSummary() {
        System.out.println("======GAME SUMMARY======");
        System.out.println("PLAYER won " + human.getScore() + " times.");
        System.out.println("COMPUTER won " + computer.getScore() + " times.");
        System.out.println("The game ended in a DRAW " + draws + " times.");
        if (human.getScore() == computer.getScore()) {
            System.out.println("This game ended in a DRAW.");
        } else if (human.getScore() > computer.getScore()) {
            System.out.println("PLAYER is the overall winner!");
        } else {
            System.out.println("COMPUTER WINS!");
        }
        System.out.println("========================");
    }

    private boolean askToPlayAgain(Scanner sc){
        int userInput = -1;
        while(userInput!=0 && userInput!=1){
            System.out.println("Would you like to play again? (No = 0, Yes = 1): ");
            userInput = sc.nextInt();
        }
        if( userInput != 0){
            return true;
        }else{
            System.out.println("Thanks for playing!");
            return false;
        }

    }
}
