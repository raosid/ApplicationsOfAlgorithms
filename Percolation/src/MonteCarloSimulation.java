import java.util.Random;

/**
 * Created by sidrao on 12/16/14.
 * Monte Carlo simulation to calculate the percolation threshold.
 */
public class MonteCarloSimulation {

    public static final int NUM_ROWS = 3;

    public static void main(String[] args) {
        Percolation p = new Percolation(NUM_ROWS);
        Random rGen = new Random();
        int countOfOpen = 0;
        while (!p.percolates()) {
            int row = rGen.nextInt(NUM_ROWS) + 1;
            int col = rGen.nextInt(NUM_ROWS) + 1;
            if (!p.isOpen(row, col)) {
                p.open(row, col);
                countOfOpen++;
            }
        }
        System.out.println((double)countOfOpen/(NUM_ROWS*NUM_ROWS));
    }
}
