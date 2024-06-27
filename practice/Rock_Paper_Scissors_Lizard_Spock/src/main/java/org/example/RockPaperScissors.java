package org.example;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {


//    public static int generateNPCMove(){
//        Random rand = new Random();
//        return (rand.nextInt(5))+1;
//    }
//
//    public static String determineWinner(int playerMove, int npcMove){
//
//        if(playerMove == npcMove){
//            return "DRAW";
//        }
//        else if((playerMove == 1 && (npcMove == 3 || npcMove == 4)) ||
//                (playerMove == 2 && (npcMove == 1 || npcMove == 5)) ||
//                (playerMove == 3 && (npcMove == 2 || npcMove == 4)) ||
//                (playerMove == 4 && (npcMove == 2 || npcMove == 5)) ||
//                (playerMove == 5 && (npcMove == 1 || npcMove == 3))
//        ){
//            return "PLAYER";
//        }
//        else return "COMPUTER";
//    }
//
//    public static String translateMove(int moveID){
//        if(moveID==1) return "ROCK.";
//        else if(moveID==2) return "PAPER.";
//        else if(moveID==3) return "SCISSORS.";
//        else if(moveID==4) return "LIZARD.";
//        else if(moveID==5) return "SPOCK.";
//        else return "INVALID MOVE...";
//    }
//
//    public static String moveCombinations(int move1, int move2){
//        if((move1 == 1 && move2 == 3) || (move1 == 3 && move2 == 1)){
//            return "Rock crushes Scissors (as it always has).";
//        }
//        else if((move1 == 1 && move2 == 4) || (move1 == 4 && move2 == 1)){
//            return "Rock crushes Lizard.";
//        }
//        else if((move1 == 2 && move2 == 1) || (move1 == 1 && move2 == 2)){
//            return "Paper covers Rock.";
//        }
//        else if((move1 == 2 && move2 == 5) || (move1 == 5 && move2 == 2)){
//            return "Paper disproves Spock.";
//        }
//        else if((move1 == 3 && move2 == 2) || (move1 == 2 && move2 == 3)){
//            return "Scissors cuts Paper.";
//        }
//        else if((move1 == 3 && move2 == 4) || (move1 == 4 && move2 == 3)){
//            return "Scissors decapitates Lizard.";
//        }
//        else if((move1 == 4 && move2 == 2) || (move1 == 2 && move2 == 4)){
//            return "Lizard eats Paper.";
//        }
//        else if((move1 == 4 && move2 == 5) || (move1 == 5 && move2 == 4)){
//            return "Lizard poisons Spock.";
//        }
//        else if((move1 == 5 && move2 == 1) || (move1 == 1 && move2 == 5)){
//            return "Spock vaporizes Rock.";
//        }
//        else if((move1 == 5 && move2 == 3) || (move1 == 3 && move2 == 5)){
//            return "Spock smashes Scissors.";
//        }
//        else{
//            return "Nothing interesting happens...";
//        }
//
//    }
//
//    public static void main(String[] args) {
//
//        while(true){
//
//            Scanner sc = new Scanner(System.in);
//
//            int playerWins = 0;
//            int compWins = 0;
//            int draws = 0;
//
//
//            System.out.println("How many rounds would you like to play? (Min: 1, Max: 10) : ");
//            int NUM_OF_ROUNDS = sc.nextInt();
//
//            if(NUM_OF_ROUNDS <=0 || NUM_OF_ROUNDS>10) {
//                System.out.println("Invalid number of rounds");
//                System.exit(0);
//            }
//            else {
//                for (int i = 1; i <= NUM_OF_ROUNDS; i++) {
//                    System.out.println(" ");
//                    System.out.println("ROUND " + i + " STARTED!");
//                    int playerChoice = 0;
//
//                    while (playerChoice > 5 || playerChoice <= 0) {
//                        System.out.println("Please select your move (1 - Rock ; 2 - Paper ; 3 - Scissors ; 4 -  Lizard ; 5 - Spock): ");
//                        playerChoice = sc.nextInt();
//                    }
//                    System.out.println("PLAYER has selected " + translateMove(playerChoice));
//
//                    int npcChoice = generateNPCMove();
//                    System.out.println("COMPUTER has selected " + translateMove(npcChoice));
//
//                    String winner = determineWinner(playerChoice, npcChoice);
//                    String combo = moveCombinations(playerChoice, npcChoice);
//
//                    System.out.println(combo);
//
//                    switch (winner) {
//                        case "PLAYER":
//                            System.out.println("PLAYER HAS WON THIS ROUND.");
//                            playerWins++;
//                            break;
//
//                        case "COMPUTER":
//                            System.out.println("COMPUTER HAS WON THIS ROUND.");
//                            compWins++;
//                            break;
//
//                        case "DRAW":
//                            System.out.println("THIS ROUND ENDED IN A DRAW.");
//                            draws++;
//                            break;
//
//                        default:
//                            System.out.println("SOMETHING WENT WRONG...");
//                    }
//                }
//                System.out.println(" ");
//                System.out.println("======GAME SUMMARY======");
//                System.out.println("PLAYER won " + playerWins + " times.");
//                System.out.println("COMPUTER won " + compWins + " times.");
//                System.out.println("The game ended in a draw " + draws + " times.");
//                if (playerWins == compWins) {
//                    System.out.println("This game ended in a DRAW.");
//                } else if (playerWins > compWins) {
//                    System.out.println("PLAYER is the overall winner!");
//                } else {
//                    System.out.println("COMPUTER is the overall winner!");
//                }
//            }
//
//            System.out.println(" ");
//
//            int userInput = -1;
//            while(userInput!=0 && userInput!=1){
//                System.out.println("Would you like to play again? (0 = No, 1 = Yes)");
//                userInput = sc.nextInt();
//            }
//            if(userInput==0){
//                System.out.println("Thanks for playing!");
//                System.exit(0);
//                break;
//            }
//
//
//        }
//
//    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Player human = new HumanPlayer();
        Player computer = new ComputerPlayer();
        Game game = new Game(human, computer);
        game.startGame();
    }

}