package com.smoothstack.matthewcrowell.twodimensionalarray;

/**
 *
 */
public class Main {

    public static void main(String[] args) {
        // initialize array
        Integer[][] array2D = new Integer[10][10];

        // assign values to array
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                array2D[i][j] = (int)(Math.random() * 1000 + 1);
            }
        }

        FindLargest(array2D);
    }

    public static void FindLargest(Integer[][] array2D){
        Integer largestNumber = 0;
        Integer row = 0;
        Integer column = 0;

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(array2D[i][j] > largestNumber){
                    row = i;
                    column = j;
                    largestNumber = array2D[row][column];

                }
            }
        }
        System.out.println("Largest number: " + largestNumber + "\nLocation in array: [" + row + "][" + column + "]");
    }
}