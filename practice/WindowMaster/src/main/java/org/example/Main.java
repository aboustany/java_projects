package org.example;

import java.util.Scanner;

public class Main {

    final static float PRICE_PER_AREA = 3.50f;
    final static float PRICE_PER_PERIM = 2.25f;

    static Scanner myScanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println("Pleas enter the height of the window (in feet):");
        float height = getHeight();

        System.out.println("Please enter the width of the window (in feet):");
        float width = getWidth();

        float windowArea = computeWindowArea(height, width);
        System.out.println("Area of the window: " + windowArea +" feet");

        float windowPerim = computeWindowPerim(height, width);
        System.out.println("Perimeter of the window: " + windowPerim +" feet");

        float windowCost = computeCost(windowArea, windowPerim);
        System.out.println("Total cost of the window: $" + windowCost);
    }

    public static float getHeight(){
        String strHeight = myScanner.nextLine();
        return Float.parseFloat(strHeight);
    }

    public static float getWidth(){
        String strWidth = myScanner.nextLine();
        return Float.parseFloat(strWidth);
    }
    public static float computeWindowArea(float h, float w){
        return w*h;
    }
    public static float computeWindowPerim(float h, float w){
        return 2*w+2*h;
    }

    public static float computeCost(float wArea, float wPerim){
        return wArea*PRICE_PER_AREA + wPerim*PRICE_PER_PERIM;
    }


}