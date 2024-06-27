package org.example.AbstractClassExample;

public class Circle extends Shape{
    private final double pi = Math.PI;
    private double r;

    public Circle() {
        this.r = 6;
    }
    public Circle(double r) {
        this.r = r;
    }
    @Override
    public double getPerimeter() {
        return 2*r*pi;
    }

    @Override
    public double getArea() {
        return r*r*pi;
    }
}
