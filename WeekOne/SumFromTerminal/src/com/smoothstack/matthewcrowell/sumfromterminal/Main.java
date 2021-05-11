package com.smoothstack.matthewcrowell.sumfromterminal;

public class Main {

    public static void main(String[] args) {
        try{
            // initialize sum
            Integer sum = 0;

            // calculate sum
            for(String argument : args){
                sum += Integer.valueOf(argument);
            }

            // print sum
            System.out.println(sum);
        }
        catch(RuntimeException e){
            e.printStackTrace();
        }
    }
}
