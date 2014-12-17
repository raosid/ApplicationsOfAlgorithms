/**
 * Created by sidrao on 12/15/14.
 * Assignment by Robert Sedgewick and Kevin Wayne
 * Used to estimate the value of percolation threshold using
 * the Monte Carlo simulation.
 */
public class Percolation {

    private boolean[][] sites;
    private WeightedQuickUnionUF grid; //used to keep track of connections.
    private int numRows;

    /*
        Constructs a N-N grid. With all sites blocked.
     */
    public Percolation(int size) {
        numRows = size;
        sites = new boolean[size][size];
        grid = new WeightedQuickUnionUF(numRows * numRows + 2); //+2 for two virtual sites.

        //connecting the virtual sites to the top and the bottom row
        for (int i = 0; i < size; i++) {
            grid.union(0, i + 1);
            grid.union(numRows * numRows + 1, numRows * numRows - i);
        }
    }

    /*
        Opens the site at (i, j) if it's not already open
        i -> row number
        j -> column number
        Pre: Has to be a valid index. Throws illegalArgument exception if not.
    */
    public void open(int i, int j) {
        if ((i < 1 || i > numRows) || (j < 1 || j > numRows)) {
            throw new IllegalArgumentException("Row/Column combination does not exist.");
        }

        //Indexes in the array
        int rowIndex = i - 1;
        int columnIndex = j - 1;

        if (!sites[rowIndex][columnIndex]) { //if closed
            sites[rowIndex][columnIndex] = true;

            int gridIndexOfCurrent = gridIndexFromXY(rowIndex, j);

            if (columnIndex + 1 < numRows && sites[rowIndex][columnIndex + 1]) { //Right
                int gridIndexOfRight = gridIndexFromXY(rowIndex, j + 1);
                grid.union(gridIndexOfCurrent, gridIndexOfRight);
            }
            if (columnIndex - 1 >= 0 && sites[rowIndex][columnIndex - 1]) { //Left
                int gridIndexOfLeft = gridIndexFromXY(rowIndex, j - 1);
                grid.union(gridIndexOfCurrent, gridIndexOfLeft);
            }
            if (rowIndex - 1 >= 0 && sites[rowIndex - 1][columnIndex]) { //Top
                int gridIndexOfTop = gridIndexFromXY(rowIndex - 1, j);
                grid.union(gridIndexOfCurrent, gridIndexOfTop);
            }
            if (rowIndex + 1 < numRows && sites[rowIndex + 1][columnIndex]) { //Bottom
                int gridIndexOfBottom = gridIndexFromXY(rowIndex + 1, j);
                grid.union(gridIndexOfCurrent, gridIndexOfBottom);
            }
        }
    }

    /*
        Returns an index in the grid Data Structure
     */
    private int gridIndexFromXY(int row, int column) {
        return row * numRows + column;
    }

    /*
        Is site (i,j) open? True if yes, false otherwise.
     */
    public boolean isOpen(int i, int j) {
        return sites[i -1][j -1];
    }

    /*
        Returns true if the given site is full, false otherwise.
        Full site: A full site is an open site that can be connected to an open
        site in the top row via a chain of neighboring (left, right, up, down)
        open sites.
        i: row number, j: column number
     */
    public boolean isFull(int i, int j) {
        return grid.connected(0, gridIndexFromXY(i -1, j -1));
    }


    /*
     * Does the system percolate? Returns true if it does. False otherwise.
     */
    public boolean percolates() {
        return grid.connected(0, numRows * numRows + 1);
    }
}