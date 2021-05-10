package com.smoothstack.matthewcrowell.numberguessinggame;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Integer number = (int) ((Math.random() * 100) + 1);
        Integer attempts = 5;
        Integer guess = Integer.valueOf(args[0]);
        System.out.println(number);
        Boolean isWinner = false;
        while(attempts > 0 && isWinner == false)
        {
            if(Math.abs(number - guess) < 11){
                System.out.println(number);
                isWinner = true;
            }
            else{
                System.out.print("Please guess again: ");
                guess = input.nextInt();
                attempts--;
            }
        }

        if(!isWinner){
            System.out.println("Sorry, " + number);
        }

    }
}
