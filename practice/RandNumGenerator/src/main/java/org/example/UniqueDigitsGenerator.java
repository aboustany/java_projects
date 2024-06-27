package org.example;

import java.util.Random;

public class UniqueDigitsGenerator {
    public static String generateUniqueDigits(){
        boolean[] usedDigit = new boolean[10];
        String result = "";
        Random r = new Random();

        while(result.length() < 4){
            int digit = r.nextInt(9);
//          System.out.println(digit);
            if (!usedDigit[digit]) { // Check if digit is not already used
                usedDigit[digit] = true; // Mark digit as used
                result += digit; // Append digit to the result
            }
        }
        return result;
    }
    public static void main(String[] args) {
        String uniqueDigits = generateUniqueDigits();
        System.out.println("Generated 4-digit number with unique digits: " + uniqueDigits);
    }
}