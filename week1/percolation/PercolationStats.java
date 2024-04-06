import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double CONFIDENCE_95 = 1.96;
  private double[] percThreshold;
  private int trials;

  // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    this.trials = trials;
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("n and trials must be > 0");
    }

    percThreshold = new double[trials];
    for (int i = 0; i < trials; i++) {
      Percolation perc = new Percolation(n);

      int[] numbers = randomNumbers(n);
      for (int y = 0; y < numbers.length; y++) {
        int pos = numbers[y];
        int col = pos % n + 1;
        int row = pos / n + 1;
        perc.open(row, col);

        if (perc.percolates()) {
          int t = perc.numberOfOpenSites();
          percThreshold[i] = (double) t / (n * n);
          break;
        }
      }
    }
  }

  private int[] randomNumbers(int n) {
    int[] numbers = new int[n * n];
    for (int i = 0; i < n * n; i++) {
      numbers[i] = i;
    }
    StdRandom.shuffle(numbers);

    return numbers;
  }

  // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(percThreshold);
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(percThreshold);
  }

  // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(trials);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(trials);
  }

  // test client (see below)
  public static void main(String[] args) {
    if (args.length != 2) {
      throw new IllegalArgumentException(
          "must provide grid size and trials e.g. ./PercolationStats 2 10000");
    }
    PercolationStats p = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    StdOut.println("mean = " + p.mean());
    StdOut.println("stddev = " + p.stddev());
    StdOut.println("95% confidence interval = [" + p.confidenceLo() + "," + p.confidenceHi() + "]");
  }
}