import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;
class Perceptron{

    static int MAX_ITER = 100;
    static double LEARNING_RATE = .1;
    static int NUM_INSTANCES = 100;
    static int theta = 0;

    public static void main(String args[]){

        double[] x = new double [NUM_INSTANCES];
        double[] y = new double [NUM_INSTANCES];
        double[] z = new double [NUM_INSTANCES];

        int[] outputs = new int [NUM_INSTANCES];

        for(int i = 0; i < NUM_INSTANCES/2; i++){
            x[i] = randomNumber(5, 10);
            y[i] = randomNumber(4, 8);
            z[i] = randomNumber(2, 9);
            outputs[i] = 1;
            System.out.println(x[i]+"\t"+y[i]+"\t"+z[i]+"\t"+outputs[i]);
        }


        for(int i = 50; i < NUM_INSTANCES; i++){
            x[i] = randomNumber(-1, 3);
            y[i] = randomNumber(-4, 2);
            z[i] = randomNumber(-3, 5);
            outputs[i] = 0;
            System.out.println(x[i]+"\t"+y[i]+"\t"+z[i]+"\t"+outputs[i]);

        }

        double[] weights = new double[4];
        double localError, globalError;
        int i, p, iteration, output;

        weights[0] = randomNumber(0, 1);
        weights[1] = randomNumber(0, 1);
        weights[2] = randomNumber(0, 1);
        weights[3] = randomNumber(0, 1);

        iteration = 0;
        do{
            iteration++;
            globalError = 0;
            for(p=0; p < NUM_INSTANCES; p++){
                output = calculateOutput(theta, weights, x[p], y[p], z[p]);
                localError = outputs[p] - output;

                weights[0] += LEARNING_RATE * localError * x[p];
                weights[1] += LEARNING_RATE * localError * x[p];
                weights[2] += LEARNING_RATE * localError * x[p];
                weights[3] += LEARNING_RATE * localError * x[p];
                globalError += localError*localError;
            }
            System.out.println("Iteration "+iteration+" : RMSE = "+
                    Math.sqrt(globalError/NUM_INSTANCES));
        } while (globalError != 0 && iteration <= MAX_ITER);

        System.out.println("\n=====\nDecision boundary equation:");
        System.out.println(weights[0] +"*x + "+weights[1]+"*y + "+weights[2]+"*z +"
            +weights[3]+" = 0");

        /* generate 10 new random points and check their classes
        notice the range of -10 and 10 means the new point could be of class 1 or 0
        - 10 to 10 covers all the ranges we used in generating the 50 classes of 1's and above

        */
        for(int j = 0; j < 10; j++){
            double x1 = randomNumber(-10, 10);
            double y1 = randomNumber(-10, 10);
            double z1 = randomNumber(-10, 10);

            output = calculateOutput(theta, weights, x1, y1, z1);
            System.out.println("\n==========\nNew Random Point:");
            System.out.println("x = "+x1+",y = "+y1+",z = "+z1);
            System.out.println("class = "+output);

        }

    }//end main

    /*
    return a random double value within a given range
    @param min the minimum value of the required range (int)



    */
    public static double randomNumber(int min, int max){
        DecimalFormat df = new DecimalFormat("#.####");
        double d = min + Math.random()*(max - min);
        String s = df.format(d);
        double x = Double.parseDouble(s);
        return x;
    }

    /*
    returns either a 1 or a 0 using a threshold function.

    */

    public static int calculateOutput(int theta, double weights[], double x, double y, double z){
        double sum = x*weights[0] + y*weights[1] + z*weights[2] + weights[3];
        return (sum >= theta) ? 1 : 0;
    }



}


