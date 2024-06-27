package org.example;

import java.util.Scanner;

public class App {

    public static void displayOperations(){
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");
        System.out.println("3. Multiplication");
        System.out.println("4. Division");
        System.out.println(" ");
        System.out.println("0. Quit");
    }

    private static double promptForOperand(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (!sc.hasNextDouble()) {
            System.out.println("That's not a valid number. Please enter a valid number:");
            sc.next(); // discard the non-double input
        }
        return sc.nextDouble();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calculator = new Calculator();


        while(true){
            System.out.println("================CALCULATOR================");
            System.out.println(" ");
            System.out.println("Please select your operation of choice:");
            displayOperations();
            int opChoice = sc.nextInt();

            if(opChoice == 0){
                System.out.println("Thank you for using this calculator! Goodbye.");
                break;
            }

            double op1 = promptForOperand(sc, "Please enter the first Operand: ");
            double op2 = promptForOperand(sc, "Please enter the second Operand: ");
            double result;

            switch (opChoice) {
                case 1:
                    result = calculator.add(op1, op2);
                    System.out.println("The result is: " + result);
                    break;
                case 2:
                    result = calculator.sub(op1, op2);
                    System.out.println("The result is: " + result);
                    break;
                case 3:
                    result = calculator.multiply(op1, op2);
                    System.out.println("The result is: " + result);
                    break;
                case 4:
                    if (op2 == 0) {
                        System.out.println("Error: Division by zero!");
                    } else {
                        result = calculator.divide(op1, op2);
                        System.out.println("The result is: " + result);
                    }
                    break;
                default:
                    System.out.println("Invalid operation selected. Please try again.");
            }
            System.out.println(" ");
        }
        sc.close();
    }


}
