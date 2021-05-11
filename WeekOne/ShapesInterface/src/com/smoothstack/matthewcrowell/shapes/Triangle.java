package com.smoothstack.matthewcrowell.shapes;

public class Triangle implements Shape {

    private Integer base;
    private Integer height;

    public Triangle(){
        this(0, 0);
    }

    public Triangle(int base, int height){
        this.base = base;
        this.height = height;
    }

    @Override
    public Integer calculateArea() {
        return (base * height)/2;
    }

    @Override
    public void display() {
        System.out.println("Base: " + base + "\nHeight: " + height + "\nArea: " + calculateArea());

    }
}
