package com.smoothstack.matthewcrowell.shapes;

public class Main{
    public static void main(String[] args){
        try{
            Rectangle rect = new Rectangle(5);
            rect.display();

            Triangle tri = new Triangle(10, 8);
            tri.display();

            Circle circ = new Circle(7);
            circ.display();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}