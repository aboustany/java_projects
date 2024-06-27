package org.example.AbstractClassExample;

public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(){
        this.height = 4;
        this.width = 7;
    }

    public Rectangle(double height, double width){
        this.height = height;
        this.width = width;
    }
    @Override
    public double getPerimeter() {
        return (width+height)*2;
    }

    @Override
    public double getArea() {
        return width*height;
    }
}
