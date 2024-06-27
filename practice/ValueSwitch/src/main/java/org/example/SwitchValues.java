package org.example;

public class SwitchValues {

    public static void switchIntegers(int a, int b) {
        a = a + b; // Sum both numbers and assign to 'a'
        b = a - b; // Subtract 'b' from the new 'a' to get original 'a' value and assign to 'b'
        a = a - b; // Subtract new 'b' from new 'a' to get original 'b' value and assign back to 'a'
        System.out.println("After switch: a = " + a + ", b = " + b);
    }

    public static void switchStrings(String a, String b) {
        a = a + b; // Concatenate both strings and assign to 'a'
        b = a.substring(0, a.length() - b.length()); // Extract original 'a' value and assign to 'b'
        a = a.substring(b.length()); // Extract original 'b' value by removing 'b' from the new 'a'
        System.out.println("After switch: a = " + a + ", b = " + b);
    }

    public static void main(String[] args) {
        int a = 5;
        int b = 10;
        System.out.println("Before switch - Integers: a = " + a + ", b = " + b);
        switchIntegers(a, b);

        String str1 = "This is fun";
        String str2 = "Java";
        System.out.println("Before switch - Strings: a = " + str1 + ", b = " + str2);
        switchStrings(str1, str2);
    }
}