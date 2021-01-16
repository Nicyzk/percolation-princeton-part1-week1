import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int size;
    private WeightedQuickUnionUF uf;
    private boolean[][] grid;
    private int noOfOpenSites;
    private int topVirtual;
    private int bottomVirtual;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be at least 1.");
        }
        noOfOpenSites = 0;
        size = n;
        uf = new WeightedQuickUnionUF(n * n + 2);
        topVirtual = n * n;
        bottomVirtual = n * n + 1;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int encode(int row, int col) {
        return row * size + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validatePosition(row, col);
        row--;
        col--;
        if (grid[row][col]) {
            return;
        }
        else {
            grid[row][col] = true;
            noOfOpenSites++;
        }
        int currentIndex = encode(row, col);
        if (row == 0) {
            // connect top row to virtual top
            uf.union(topVirtual, currentIndex);
        }
        if (row == size - 1) {
            // connect bottom row to virtual bottom
            uf.union(bottomVirtual, currentIndex);
        }

        // check whether surrounding sites are valid and open. Connect if open.
        if (row - 1 >= 0 && grid[row - 1][col]) {
            uf.union(encode(row - 1, col), currentIndex);
        }
        if (col + 1 < size && grid[row][col + 1]) {
            uf.union(encode(row, col + 1), currentIndex);
        }
        if (row + 1 < size && grid[row + 1][col]) {
            uf.union(encode(row + 1, col), currentIndex);
        }
        if (col - 1 >= 0 && grid[row][col - 1]) {
            uf.union(encode(row, col - 1), currentIndex);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validatePosition(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validatePosition(row, col);
        int currentIndex = encode(row - 1, col - 1);
        return uf.find(currentIndex) == uf.find(topVirtual);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return noOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(topVirtual) == uf.find(bottomVirtual);
    }

    private void validatePosition(int row, int col) {
        // rmb row and col will start from (1, 1) to (n, n). But in our grid, we start from (0, 0) so we must adjust.
        // if (row <= 0 || row > size || col <= 0 || col > size) {
        //     throw new IllegalArgumentException("row or column input is out of bounds");
        // }
        if (row > size || row <= 0 || col > size || col <= 0)
            throw new IllegalArgumentException(
                    "row or col is out of bounds. Input is (" + row + ", " + col + ")");
    }

    // test client (optional)
    // public static void main(String[] args)
}


