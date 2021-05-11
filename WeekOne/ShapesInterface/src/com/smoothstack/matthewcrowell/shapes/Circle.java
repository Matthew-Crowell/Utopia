package com.smoothstack.matthewcrowell.shapes;

public class Circle implements Shape {

    private Integer radius;

    public Circle(){
        this(0);
    }

    public Circle(int size) {
        this.radius = size;
    }

    @Override
    public Integer calculateArea() {

        return (int)((radius * radius) * Math.PI);
    }

    @Override
    public void display() {
        System.out.println("Radius:  " + radius + "\nArea: " + calculateArea());

    }
}
