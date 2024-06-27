package org.example;

import java.util.Random;
import java.util.Scanner;

public class SumOfDigits {
    public static int sumDigits(int number){
        String strNumb = String.valueOf(number);
        int sum = 0;
        int newNum = number;

        for (int i=1; i <= strNumb.length(); i++){
            System.out.println("Digit being summed:"+ newNum%10);
            sum+= newNum%10;
            newNum = newNum/10;
        }
        return sum;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please input number to sum its digits: " );
        int inputNumb = sc.nextInt();
        System.out.println("Sum of the digits of "+ inputNumb+" is:"+ sumDigits(inputNumb));
    }
}
