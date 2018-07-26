import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] means = null;
    private int numberOfTrials = 0;
    private double mean = 0;
    private double stddev = 0;
    private double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        } else {
            this.means = new double[trials];
            this.numberOfTrials = trials;
            for (int i = 0; i < trials; i++) {
                Percolation p = new Percolation(n);
                while (!p.percolates()) {
                    int row = StdRandom.uniform(n) + 1;
                    int col = StdRandom.uniform(n) + 1;
                    if (!p.isOpen(row, col)) {
                        p.open(row, col);
                    }
                }
                this.means[i] = (double) p.numberOfOpenSites() / (n * n);
            }
            this.mean = StdStats.mean(this.means);
            this.stddev = StdStats.stddev(this.means);
        }
    }

    public double mean() {
        return this.mean;
    }

    public double stddev() {
        return this.stddev;
    }

    public double confidenceLo() {
        return this.mean - this.stddev * CONFIDENCE_95 / Math.sqrt(this.numberOfTrials);
    }

    public double confidenceHi() {
        return this.mean + this.stddev * CONFIDENCE_95 / Math.sqrt(this.numberOfTrials);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}