import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private static final boolean BLOCKED = false;
  private static final boolean OPEN = true;
  private static final int TOP = 0;

  private final int N;
  private final int BOTTOM;

  private boolean[] sites;
  private int openSites = 0;
  private WeightedQuickUnionUF wquuf;
  private WeightedQuickUnionUF backwash;

  // create N-by-N grid with all sites blocked
  public Percolation(int n) {
    if (n < 1) throw new IllegalArgumentException("n must be > 1");

    N = n;
    BOTTOM = N * N + 1;
    wquuf = new WeightedQuickUnionUF(BOTTOM + 1); // virtual top and  bottom
    backwash = new WeightedQuickUnionUF(BOTTOM); // v. top, no v. bottom
    sites = new boolean[BOTTOM + 1]; // v. top and bottom

    // virtual top and bottom always open
    sites[TOP] = OPEN;
    sites[BOTTOM] = OPEN;
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
    this.validate(row, col);

    if (!this.isOpen(row, col)) {
      openSites++;
      sites[this.xyTo1D(row, col)] = OPEN;
      this.connect(row, col);
    }
  }

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
    this.validate(row, col);
    return sites[this.xyTo1D(row, col)];
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) {
    this.validate(row, col);
    return backwash.connected(xyTo1D(row, col), TOP);
  };

  // number of open sites
  public int numberOfOpenSites() {
    return openSites;
  }

  // does the system percolate?
  public boolean percolates() {
    return wquuf.connected(TOP, BOTTOM);
  }

  // conect site at (row, col) to all open sites around it
  // also connect site to virtual top/bottom if applicable
  private void connect(int row, int col) {
    int[][] deltas = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    for (int i = 0; i < deltas.length; i++) {
      int nextRow = row + deltas[i][0];
      int nextCol = col + deltas[i][1];
      if (this.isValid(nextRow, nextCol) && this.isOpen(nextRow, nextCol)) {
        this.union(xyTo1D(row, col), xyTo1D(nextRow, nextCol));
      }
    }

    if (row == 1) this.union(TOP, xyTo1D(row, col));
    if (row == N) wquuf.union(BOTTOM, xyTo1D(row, col));
  }

  // union in both backwash-prone wquuf and in anti-backwash wquuf
  private void union(int p, int q) {
    wquuf.union(p, q);
    backwash.union(p, q);
  }

  // is site (row, col) on the grid?
  private boolean isValid(int row, int col) {
    return this.isValid(row) && this.isValid(col);
  }

  // is i a valid row/col number?
  private boolean isValid(int i) {
   return i > 0 && i <= N;
  }

  // map from 2-D to 1-D where all coords are 1-indexed
  private int xyTo1D(int row, int col) {
    return N * (row - 1) + (col % (N + 1));
  }

  // throw error if i is not valid row/col number
  private void validate(int i) {
    if (i <= 0 || i > N) throw new IllegalArgumentException("row index i out of bounds");
  }

  // validate 2-D site coords
  private void validate(int i, int j) {
    this.validate(i);
    this.validate(j);
  }

  // pretty-print current state open/closed sited
  private void printSites() {
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        char open = this.isOpen(i, j) ? 'o' : 'x';
        System.out.print(" " + open + " ");
      }
      System.out.println();
    }
  }

  // test client (optional)
  public static void main(String[] args) {
    Percolation p = new Percolation(4);
    p.open(1,1);
    p.open(2, 1);
    p.open(3, 1);
    p.open(3, 2);
    p.open(4, 2);
    System.out.println(p.percolates());
    p.printSites();
  }
}
