package com.smoothstack.matthewcrowell.shapes;

public class Rectangle implements Shape {

    private Integer length;
    private Integer width;

    public Rectangle(){
        this(0);
    }

    public Rectangle(int size){
        this(size, size);
    }

    public Rectangle(int length, int width){
        this.length = length;
        this.width = width;
    }

    @Override
    public Integer calculateArea() {
        return length * width;
    }

    @Override
    public void display() {
        System.out.println("Length: " + length + "\nWidth: " + width + "\nArea: " + calculateArea());

    }
}
