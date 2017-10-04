public class Percolation {
  private static final int BLOCKED = 0;
  private static final byte OPEN = 1;
  private static final byte FULL = 2;
  private static final byte TOP = 0;

  private final int N;
  private byte[] sites;
  private int openSites = 0;
  private boolean percolates = false;

  // create N-by-N grid with all sites blocked
  public Percolation(int n) {
    if (n < 1) throw new IllegalArgumentException("n must be > 1");

    N = n;
    sites = new byte[N * N + 1]; // v. top, no bottom
    sites[TOP] = OPEN; // virtual top always open
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
    int pos = this.xyTo1D(row, col);
    return sites[pos] == OPEN || sites[pos] == FULL;
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) {
    this.validate(row, col);
    return sites[this.xyTo1D(row, col)] == FULL;
  };

  // number of open sites
  public int numberOfOpenSites() {
    return openSites;
  }

  // does the system percolate?
  public boolean percolates() {
    return percolates;
  }

  // conect site at (row, col) to all open sites around it
  // also connect site to virtual top/bottom if applicable
  private void connect(int row, int col) {
    int[][] deltas = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    if (row == 1) this.fill(row, col);

    for (int i = 0; i < deltas.length; i++) {
      int nextRow = row + deltas[i][0];
      int nextCol = col + deltas[i][1];

      if (!this.isValid(nextRow, nextCol)) continue;
      if (this.isFull(nextRow, nextCol)) this.fill(row, col);
    }
  }

  // recursively fill sites
  private void fill(int row, int col) {
    if (this.isFull(row, col) || !this.isOpen(row, col)) return;

    sites[this.xyTo1D(row, col)] = FULL;
    if (row == N) percolates = true;

    int[][] deltas = { {1, 0}, {0, 1}, {-1, 0}, {0, -1} };

    for (int i = 0; i < deltas.length; i++) {
      int nextRow = row + deltas[i][0];
      int nextCol = col + deltas[i][1];

      if (!this.isValid(nextRow, nextCol)) continue;
      this.fill(nextRow, nextCol);
    }
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
        char site = this.isOpen(i, j) ? 'o' : 'x';
        site = this.isFull(i, j) ? 'f' : site;
        System.out.print(" " + site + " ");
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
    p.open(2, 3);
    p.open(4, 2);
    System.out.println(p.percolates());
    p.printSites();
  }
}
