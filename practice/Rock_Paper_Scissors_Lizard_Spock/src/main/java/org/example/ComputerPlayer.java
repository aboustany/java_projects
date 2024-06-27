package org.example;

import java.util.Random;

class ComputerPlayer extends Player {
    public Move makeMove() {
        Random rand = new Random();
        int moveID = rand.nextInt(5) + 1;
        return Move.fromID(moveID);
    }
}
