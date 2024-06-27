package org.example;

enum Move {
    ROCK(1, "Rock"),
    PAPER(2, "Paper"),
    SCISSORS(3, "Scissors"),
    LIZARD(4, "Lizard"),
    SPOCK(5, "Spock");

    private final int moveID;
    private final String name;

    Move(int moveID, String name) {
        this.moveID = moveID;
        this.name = name;
    }

    public int getMoveID() {
        return moveID;
    }

    public String getName() {
        return name;
    }

    public static Move fromID(int id) {

        for (Move move : Move.values()) {
            if (move.getMoveID() == id) {
                return move;
            }
        }
        return null;
    }

    public static String moveCombinations(int move1, int move2){
        if((move1 == 1 && move2 == 3) || (move1 == 3 && move2 == 1)){
            return "Rock crushes Scissors (as it always has).";
        }
        else if((move1 == 1 && move2 == 4) || (move1 == 4 && move2 == 1)){
            return "Rock crushes Lizard.";
        }
        else if((move1 == 2 && move2 == 1) || (move1 == 1 && move2 == 2)){
            return "Paper covers Rock.";
        }
        else if((move1 == 2 && move2 == 5) || (move1 == 5 && move2 == 2)){
            return "Paper disproves Spock.";
        }
        else if((move1 == 3 && move2 == 2) || (move1 == 2 && move2 == 3)){
            return "Scissors cuts Paper.";
        }
        else if((move1 == 3 && move2 == 4) || (move1 == 4 && move2 == 3)){
            return "Scissors decapitates Lizard.";
        }
        else if((move1 == 4 && move2 == 2) || (move1 == 2 && move2 == 4)){
            return "Lizard eats Paper.";
        }
        else if((move1 == 4 && move2 == 5) || (move1 == 5 && move2 == 4)){
            return "Lizard poisons Spock.";
        }
        else if((move1 == 5 && move2 == 1) || (move1 == 1 && move2 == 5)){
            return "Spock vaporizes Rock.";
        }
        else if((move1 == 5 && move2 == 3) || (move1 == 3 && move2 == 5)){
            return "Spock smashes Scissors.";
        }
        else{
            return "Nothing interesting happens...";
        }

    }
}