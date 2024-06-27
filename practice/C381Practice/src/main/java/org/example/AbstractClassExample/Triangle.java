package org.example.AbstractClassExample;

public class Triangle extends Shape{
    private double side1;
    private double side2;
    private double base;
    private double height;

    public Triangle(){
        this.side1 = 3;
        this.side2 = 4;
        this.base = 7;
        this.height = 6;
    }

    public Triangle(double side1, double side2, double base, double  height ){
        this.side1 = side1;
        this.side2 = side2;
        this.base = base;
        this.height = height;
    }
    @Override
    public double getPerimeter() {
        return side1+side2+base;
    }

    @Override
    public double getArea() {
        return height*base*0.5;
    }
}
