import java.util.Random;
import java.util.Scanner;

/**
 * Created by sidrao on 12/16/14.
 * Monte Carlo simulation to calculate the percolation threshold.
 * Lets the user carry out the experiment any number of times
 * and prints out the final number.
 */
public class MonteCarloSimulation {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("How many experiments would you like to perform? ");
        int numExperiments = console.nextInt();
        System.out.print("How many rows for each grid? ");
        int rows = console.nextInt();
        if (numExperiments <= 0 || rows <= 0) {
            throw new IllegalArgumentException();
        }
        performSimulations(numExperiments, rows);

    }

    /*
        Returns the threshold for a single experiment.
     */
    public static double MonteCarloSimulation(int rows) {
        Percolation p = new Percolation(rows);
        Random rGen = new Random();
        int countOfOpen = 0;
        while (!p.percolates()) {
            int row = rGen.nextInt(rows) + 1;
            int col = rGen.nextInt(rows) + 1;
            if (!p.isOpen(row, col)) {
                p.open(row, col);
                countOfOpen++;
            }
        }
        return ((double)countOfOpen/(rows*rows));
    }

    /*
        num: The number of simulations to be performed.
        rows: The number for rows in each grid.
        Performs the given number for Monte Carlo simulations.
     */
    public static void performSimulations(int num, int rows) {
        double[] thresholds = new double[num];
        for (int i = 0; i < num; i++) {
            thresholds[i] = MonteCarloSimulation(rows);
        }
        double mean = getMean(thresholds);
        double standardDev = getStdDev(mean, thresholds);
        System.out.println("Mean: " + mean);
        System.out.println("Standard Deviation: " + standardDev);
    }

    //Returns the mean of all thresholds.
    public static double getMean(double[] thresholds) {
        double total = 0;
        for (int i = 0; i < thresholds.length; i++) {
            total+=thresholds[i];
        }
        return total/thresholds.length;
    }

    //Returns the threshold for all calculations.
    public static double getStdDev(double mean, double[] thresholds) {
        double total = 0;
        for (int i = 0; i < thresholds.length; i++) {
            total += Math.pow((thresholds[i] - mean), 2);
        }
        return Math.pow((total / (thresholds.length - 1)), 0.5);
    }

}
