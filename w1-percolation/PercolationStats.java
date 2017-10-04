import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
  private int N;
  private int T;
  private double stddev;
  private double mean;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    N = n; T = trials;
    double[] results = new double[T];

    for (int i = 0; i < T; i++) {
      Percolation perc = new Percolation(n);
      while (!perc.percolates()) {
        int row = StdRandom.uniform(N) + 1;
        int col = StdRandom.uniform(N) + 1;
        perc.open(row, col);
      }
      results[i] = perc.numberOfOpenSites() * 1.0 / (N * N);  
    }

    mean = StdStats.mean(results);
    stddev = StdStats.stddev(results);
  }

  // sample mean of percolation threshold
  public double mean() {
    return mean; 
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    if (N == 1) return Double.NaN;
    return stddev; 
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return this.mean() - 1.96 * this.stddev() / Math.sqrt(T);
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return this.mean() + 1.96 * this.stddev() / Math.sqrt(T);
  }

  // test client (described below)
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    if (n <= 0 || t <= 0) throw new java.lang.IllegalArgumentException("bad");

    PercolationStats stats = new PercolationStats(n, t);
    System.out.println("mean                    = " + stats.mean()); 
    System.out.println("stddev                  = " + stats.mean()); 
    System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]"); 
  }
}
