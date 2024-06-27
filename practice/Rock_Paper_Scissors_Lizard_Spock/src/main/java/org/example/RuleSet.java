package org.example;

class RuleSet {
    public static String determineWinner(int playerMove, int npcMove){

        if(playerMove == npcMove){
            return "DRAW";
        }
        else if((playerMove == 1 && (npcMove == 3 || npcMove == 4)) ||
                (playerMove == 2 && (npcMove == 1 || npcMove == 5)) ||
                (playerMove == 3 && (npcMove == 2 || npcMove == 4)) ||
                (playerMove == 4 && (npcMove == 2 || npcMove == 5)) ||
                (playerMove == 5 && (npcMove == 1 || npcMove == 3))
        ){
            return "PLAYER";
        }
        else return "COMPUTER";
    }

}
