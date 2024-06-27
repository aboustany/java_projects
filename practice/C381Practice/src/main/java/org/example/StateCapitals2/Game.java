package org.example.StateCapitals2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        Map<String, String> stateCapitals = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("StateCapitals.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::");
                stateCapitals.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        System.out.println(stateCapitals.size() + " STATES & CAPITALS ARE LOADED.");
        System.out.println("=======");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> statesList = new ArrayList<>(stateCapitals.keySet());
        Collections.shuffle(statesList);
        int score = 0;
        int numberOfQuestions = 5;

        System.out.println("READY TO TEST YOUR KNOWLEDGE?");
        for (int i = 0; i < numberOfQuestions; i++) {
            String currentQuestionState = statesList.get(i);
            System.out.println("WHAT IS THE CAPITAL OF '" + currentQuestionState + "'?");
            String userGuess = scanner.nextLine().trim();

            if (stateCapitals.get(currentQuestionState).equalsIgnoreCase(userGuess)) {
                System.out.println("NICE WORK! " + userGuess + " IS CORRECT!\n");
                score++;
            } else {
                System.out.println("INCORRECT! THE CORRECT ANSWER IS " + stateCapitals.get(currentQuestionState) + ".\n");
            }
        }

        System.out.println("QUIZ COMPLETED. YOUR SCORE IS: " + score + "/" + numberOfQuestions);
    }
}
