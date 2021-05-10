package com.smoothstack.matthewcrowell.javabasics.printpatterns;
/*

Application to print specified patterns.
@author matthew.crowell

*/

public class Main {

    public static void main(String[] args) {
        System.out.println("1)");
        PrintPattern1(4);
        System.out.println("2)");
        PrintPattern2(4);
        System.out.println("3)");
        PrintPattern3(4);
        System.out.println("4)");
        PrintPattern4(4);
    }

    public static void PrintPattern1(int iterations){
        // variables for base and pattern
        String base = ".";
        String pattern = "*";

        // print by row
        for(int i = 0; i < iterations; i++)
        {
            // generate base line
            base += "..";

            // print complete row
            System.out.println(pattern);

            // increment number of pattern characters printed by one
            pattern += "*";
        }

        // print base line
        System.out.println(base);
    }

    public static void PrintPattern2(int iterations) {
        // variables for base and pattern
        String base = "..";
        String pattern = "*";

        // generate base line
        for(int i = 0; i < iterations; i++){
            base += "..";
        }

        // print base line
        System.out.println(base);

        // print by row
        for(int i = iterations; i >= 0; i--){
            // print row by column
            for(int j = 0; j < i; j++){
                System.out.print(pattern);
            }

            // complete row
            System.out.println();
        }
    }

    public static void PrintPattern3(int iterations){
        // variables for base and pattern
        String base = "...";
        String pattern = "*";

        // print by row
        for(int i = iterations; i > 0; i--){

            // spacing by column
            for(int j = i; j >= 0; j--){
                System.out.print(" ");
            }

            // print pattern by row
            System.out.println(pattern);

            // increment number of pattern characters printed by two
            pattern += "**";

            // generate base line
            base += "..";
        }

        // print base line
        System.out.println(base);
    }

    public static void PrintPattern4(int iterations) {
        // variables for base and pattern
        String base = "....";
        String pattern = "*";

        // counter for number of stars
        int patternCounter = 2 * iterations - 1;

        // generate the base line
        for(int i = 0; i < iterations; i++){
            base += "..";
        }

        // print the base line
        System.out.println(base);

        // print by row
        for (int i = iterations - 1; i >= 0; i--) {

            // print leading space
            System.out.print(" ");

            // print spacing
            for (int j = i; j < iterations; j++) {
                System.out.print(" ");
            }

            // print pattern
            for (int j = 1; j <= patternCounter; j++) {
                    System.out.print(pattern);
                }

            // end the row
            System.out.println();

            // decrement number of characters printed by 2
            patternCounter -= 2;
        }
    }
}