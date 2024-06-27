package org.example.PerimeterCalculator;

import java.util.ArrayList;
import java.util.List;

public class PerimeterCalculator {

    public static double getDistance(Point p1, Point p2){
        return Math.sqrt(Math.pow((p2.getX()-p1.getX()),2) + Math.pow((p2.getY()-p1.getY()),2));
    }

    public static double getPerimeter(List<Point> points){
        double perimeter = 0;
        for(int i=0; i<points.size(); i++){
            if(i == points.size()-1){
                perimeter+= getDistance(points.get(i), points.get(0));
            }
            else {
                perimeter += getDistance(points.get(i), points.get(i + 1));
            }
        }
        return perimeter;
    }

    public static void main(String[] args){

        Point point1 = new Point(1,2);
        Point point2 = new Point(3,1);
        Point point3 = new Point(2,1);
        Point point4 = new Point(-1,-2);
        Point point5 = new Point(3,4);
        Point point6 = new Point(-2,2);

        List<Point> points = new ArrayList<>();
        points.add(point1);
        points.add(point2);
        points.add(point3);
        points.add(point4);
        points.add(point5);
        points.add(point6);

        System.out.printf("The perimeter of this shape is %.3f%n", getPerimeter(points));
    }

}
