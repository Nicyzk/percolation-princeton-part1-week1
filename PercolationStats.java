import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] probabilities;
    private int noOfTrials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validate(n, trials);
        noOfTrials = trials;
        probabilities = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int numberOfOpenedSites = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    numberOfOpenedSites++;
                }
            }
            double fraction = (double) numberOfOpenedSites / (n * n);
            probabilities[i] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(probabilities);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(probabilities);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stdDev = stddev();
        double confidenceLo = mean - (1.96 * stdDev) / Math.sqrt(noOfTrials);
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stdDev = stddev();
        double confidenceHi = mean + (1.96 * stdDev) / Math.sqrt(noOfTrials);
        return confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println(
                "95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi()
                        + "]");
    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Input is out of valid range. Input: n = " + n + ", trials = " + trials);
        }
    }
}
